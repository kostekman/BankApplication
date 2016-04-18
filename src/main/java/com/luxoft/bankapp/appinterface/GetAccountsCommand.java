package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankService;

public class GetAccountsCommand extends AbstractCommand  implements Command {

	public GetAccountsCommand(BankService bankService) {
		super(bankService);
	}

	@Override
	public void execute(Bank bank) {
		System.out.println("List of accounts:");
		for(Account account :getActiveClient().getAccounts()){
			account.printReport();
		}

	}

	@Override
	public void printCommandInfo() {
		System.out.println("This commend shows the list of account of the active client");

	}

}
