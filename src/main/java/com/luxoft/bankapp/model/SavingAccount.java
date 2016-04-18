package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public class SavingAccount extends AbstractAccount implements Account {

	public SavingAccount(float balance) {
		super(balance);
	}
	@Override
	public void withdraw(float amount) throws BankException{
		if(amount > getBalance()){
			System.out.println("You cannot withdraw more then your current balance");
			throw new NotEnoughFundsException(amount - super.getBalance());
		}
		else
			super.withdraw(amount);
	}
	@Override
	public void printReport() {
		System.out.printf(this.toString());
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nSaving Account, balance: " + Float.valueOf(getBalance()));
		return sb.toString();
	}



}
