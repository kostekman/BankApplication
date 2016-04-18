package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankServiceImpl implements BankService {

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

}
