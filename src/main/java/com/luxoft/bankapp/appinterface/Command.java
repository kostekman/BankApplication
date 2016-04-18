package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;

public interface Command {
	public void execute(Bank bank);
	public void printCommandInfo();
}
