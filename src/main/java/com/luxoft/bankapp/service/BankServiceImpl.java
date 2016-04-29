package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;

public class BankServiceImpl implements BankService {

    @Override
    public BankInfo getBankInfo(Bank bank) {
        BankInfo bankInfo = new BankInfo();
        bankInfo.setBankReport(bank.getReport());
        return bankInfo;
    }

    @Override
    public void addClient(Bank bank, Client client) {
        bank.addClient(client);
    }

    @Override
    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public Client findClient(Bank bank, String name) {
        return bank.getClientNameMap().get(name);
    }

    @Override
    public void saveClient(Client client, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client loadClient(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Client client = (Client) ois.readObject();
            return client;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
