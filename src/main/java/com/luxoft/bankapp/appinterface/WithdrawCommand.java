package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

import static com.luxoft.bankapp.scanner.StringChecker.checkNumber;

public class WithdrawCommand extends AbstractAccountBalanceModifierCommand  implements Command {
	
	public WithdrawCommand(BankService bankService) {
		super(bankService);
	}

	@Override
	public void execute(Bank bank) {
		if(getActiveClient().getActiveAccount() == null){
			System.out.println("This client has no account yet, please create an account");
			return;
		}
		String amount = "";
		while(!isCorrectData()){
			System.out.println("Please provide how much money you want to withdraw.");
			amount = BankScanner.getScanner().nextLine();
			setCorrectData(checkNumber(amount));
			if(!isCorrectData()){
				System.out.println("The number you entered is incorrect");
			}
			
		};
		setCorrectData(false);
		withdrawFromAccount(amount);
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraws given amount of money to active account");

	}

}
