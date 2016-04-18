package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;

public abstract class AbstractAccount implements Account {
	private float balance;
	private int ID;
	private static int nextID = 1;
	private String accountType;
	
	public AbstractAccount(float balance) {
		this.balance = balance;
		this.ID = nextID++;
	}

	public AbstractAccount() {}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractAccount that = (AbstractAccount) o;

		if (ID != that.ID) return false;
		return accountType.equals(that.accountType);

	}

	@Override
	public int hashCode() {
		int result = ID;
		result = 31 * result;
		return result;
	}

	@Override
	public void printReport() {

	}
	
	@Override
	public int decimalValue(){
		return Math.round(balance);
	}

	@Override
	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Override
	public float getBalance() {
		return balance;
	}

	public int getID() {
		return ID;
	}

	@Override
	public void deposit(float x) {
		balance += x;

	}

	@Override
	public void withdraw(float x) throws BankException{
		balance -= x;

	}

}
