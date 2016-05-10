package com.luxoft.bankapp.model;

import com.luxoft.bankapp.databasemanagement.*;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.service.Report;

import java.util.*;
import java.util.logging.Level;

public class Bank implements Report {
    private int id;
    private String name;
    @NoDB
    private Set<Client> clients;
    @NoDB
    private List<ClientRegistrationListener> listeners;

    @NoDB
    private Map<String, Client> clientNameMap;

    public Bank(String name) {
        this.name = name;
        clients = new TreeSet<>(new Comparator<Client>() {
            @Override
            public int compare(Client c1, Client c2) {
                return c1.getCity().compareTo(c2.getCity());
            }
        });
        clientNameMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        listeners = new ArrayList<>();
        listeners.add(new PrintClientListener());
        listeners.add(new EmailNotificationListener());
        listeners.add(new AddClientToMapListener());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addClient(Client client) {
        clients.add(client);

        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public void removeClient(Client client) {
        clients.remove(client);
        clientNameMap.remove(client.getName());
    }

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    public Map<String, Client> getClientNameMap() {
        return Collections.unmodifiableMap(clientNameMap);
    }

    public void parseFeed(Map<String, String> feed) {
        String name = feed.get("name"); // client name
        // try to find client by his name
        Client client = clientNameMap.get(name);
        if (client == null) { // if no client then create it
            Gender gender;
            if (feed.get("gender").equals("m")) {
                gender = Gender.MALE;
            } else {
                gender = Gender.FEMALE;
            }
            client = new Client(name, feed.get("city"), gender, feed.get("email"), feed.get("phonenumber"), Float.parseFloat(feed.get("initialoverdraft")));
            clientNameMap.put(name, client);
        }
        client.parseFeed(feed);
    }


    @Override
    public void printReport() {
        System.out.println("Bank clients: ");
        for (Client c : clients) {
            c.printReport();
        }

    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bank clients: ");
        for (Client c : clients) {
            sb.append(c.getReport());
        }
        return sb.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bank bank = (Bank) o;

        if (id != bank.id) return false;
        return name != null ? name.equals(bank.name) : bank.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static Bank getBankByName(String bankname) {
        BankDAO bankDB = new BankDAOImpl();
        ClientDAO clientDB = new ClientDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            Bank bank = bankDB.getBankByName(bankname);
            List<Client> clientsList = clientDB.getAllClients(bank.getId());

            for (Client c : clientsList) {
                bank.addClient(c);
                List<Account> accountsList = accountDAO.getClientAccounts(c.getId());
                for (Account a : accountsList) {
                    c.addAccount(a);
                }
            }
            return bank;
        } catch (DAOException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, e.getMessage(), e);
        } catch (BankNotFoundException e) {
            System.out.println("Bank not found");
            e.printStackTrace();
            BankAppLogger.log(Level.INFO, e.getMessage(), e);
        }
        return new Bank("");
    }

    private class PrintClientListener implements ClientRegistrationListener {

        @Override
        public void onClientAdded(Client client) {
            client.printReport();

        }

    }

    private class EmailNotificationListener implements ClientRegistrationListener {

        @Override
        public void onClientAdded(Client client) {
            System.out.println("Notification email for client " + client.getName() + " to be sent");
        }

    }

    private class AddClientToMapListener implements ClientRegistrationListener {

        @Override
        public void onClientAdded(Client client) {
            clientNameMap.put(client.getName(), client);
        }
    }

}
