package com.luxoft.bankapp.service;

import com.luxoft.bankapp.databasemanagement.AccountDAO;
import com.luxoft.bankapp.databasemanagement.AccountDAOImpl;
import com.luxoft.bankapp.databasemanagement.ClientDAO;
import com.luxoft.bankapp.databasemanagement.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.TooManyClientsFoundException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.util.logging.Level;

public class BankServiceImpl implements BankService {
    private final static String fileName = "clients.txt";
    @Override
    public BankInfo getBankInfo(Bank bank) {
        BankInfo bankInfo = new BankInfo();
        bankInfo.setBankReport(bank.getReport());
        return bankInfo;
    }

    @Override
    public void addClient(Bank bank, Client client) {
        bank.addClient(client);
        ClientDAO clientDAO = new ClientDAOImpl();
        try {
            clientDAO.save(client, bank.getId());
        } catch (DAOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
        ClientDAO clientDAO = new ClientDAOImpl();
        try {
            clientDAO.remove(client.getId(), bank.getId());
        } catch (DAOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(Client client, Account account, int bankId) {
        client.addAccount(account);
        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            accountDAO.add(account, bankId, client.getId());
        } catch (DAOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public void depositOnAccount(Account account, float amount){
        account.deposit(amount);
        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            accountDAO.update(account);
        } catch (DAOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void withdrawFromAccount(Account account, float amount) throws BankException {
        account.withdraw(amount);
        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            accountDAO.update(account);
        } catch (DAOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public Client findClient(Bank bank, String name) {
        return bank.getClientNameMap().get(name);
    }

    @Override
    public void saveClient(Client client) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(client);
        } catch (IOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public Client loadClient() {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Client) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client loadClientFromDb(String clientName) throws TooManyClientsFoundException, DAOException, ClientNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.findClientByName(clientName);
    }
}
