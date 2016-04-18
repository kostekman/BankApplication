package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

public class AddAccountCommand extends AbstractCommand implements Command {

	public AddAccountCommand(BankService bankService) {
		super(bankService);
	}

	@Override
	public void execute(Bank bank) {
		String account = "";
		while(!isCorrectData()){
			System.out.println("Please provide account type to be created for a client checking(0) or saving(1)");
			account = BankScanner.getScanner().nextLine();
			if(!(account.equals("1") || account.equals("0"))){
				System.out.println("You have entered invalid data");
			}
			else{
				setCorrectData(true);
			}
		};
		setCorrectData(false);
		
		if(account.equals("0")){
			String checkingOverdraft = "";
			while(!isCorrectData()){
				System.out.println("Please provide overdraft for the account:");
				checkingOverdraft = BankScanner.getScanner().nextLine();
				setNumberMatcher(getNumberPattern().matcher(checkingOverdraft));
				setCorrectData(getNumberMatcher().matches());
				if(!isCorrectData()){
					System.out.println("The number you entered is incorrect");
				}
				
			};
			setCorrectData(false);
			getBankService().addAccount(getActiveClient(), new CheckingAccount(0.0f, Float.valueOf(checkingOverdraft)));
		}
		else{
			getBankService().addAccount(getActiveClient(), new SavingAccount(0.0f));
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Adds new account to active client");

	}

}
