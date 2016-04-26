package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.Report;

import java.util.*;

public class Bank implements Report {

    private Set<Client> clients;
    private List<ClientRegistrationListener> listeners;

    private Map<String, Client> clientNameMap;

    public Bank() {
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
