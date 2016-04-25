package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;

public interface Command {
	void execute(Bank bank);
	void printCommandInfo();
}
