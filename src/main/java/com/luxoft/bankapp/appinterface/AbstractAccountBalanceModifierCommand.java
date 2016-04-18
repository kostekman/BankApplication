package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;

public abstract class AbstractAccountBalanceModifierCommand extends AbstractCommand {
	
	public AbstractAccountBalanceModifierCommand(BankService bankService) {
		super(bankService);
	}

	public boolean withdrawFromAccount(String amount){
		boolean completed = false;
		try {
			getActiveClient().getActiveAccount().withdraw(Float.valueOf(amount));
			completed = true;
			System.out.printf("You have successfully withdrawed %s from the account", amount);
		} catch (BankException e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
		return completed;

	}
	
	public void depositOnAccount(String amount){
		getActiveClient().getActiveAccount().deposit(Float.valueOf(amount));
		System.out.printf("You have successfully deposited %s on the account", amount);
	}
	
	public void depositOnAccount(Client client, String amount){
		client.getActiveAccount().deposit(Float.valueOf(amount));
		System.out.printf("You have successfully deposited %s on the account", amount);
	}

}
