package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService {
    BankInfo getBankInfo(Bank bank);

    void addClient(Bank bank, Client client);

    void removeClient(Bank bank, Client client);

    void addAccount(Client client, Account account, int bankId);

    void setActiveAccount(Client client, Account account);

    void depositOnAccount(Account account, float amount);

    void withdrawFromAccount(Account account, float amount) throws BankException;

    Client findClient(Bank bank, String name);

    void saveClient(Client client);

    Client loadClient();
}
