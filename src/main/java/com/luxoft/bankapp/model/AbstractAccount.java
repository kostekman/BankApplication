package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;

public abstract class AbstractAccount implements Account {
	private float balance;
	private int ID;
	private static int nextID = 1;
	
	public AbstractAccount(float balance) {
		this.balance = balance;
		this.ID = nextID++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractAccount))
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public void printReport() {

	}
	
	@Override
	public int decimalValue(){
		return Math.round(balance);
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
