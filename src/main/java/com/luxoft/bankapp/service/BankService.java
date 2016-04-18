package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService {
	public void addClient(Bank bank, Client client);
	public void removeClient(Bank bank, Client client);
	public void addAccount(Client client, Account account);
	public void setActiveAccount(Client client, Account account);
	public Client findClient(Bank bank, String name);
}
