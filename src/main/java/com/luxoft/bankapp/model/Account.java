package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.service.Report;

public interface Account extends Report{
	public float getBalance();
	public void deposit(float x);
	public void withdraw(float x) throws BankException;
	public int decimalValue();
}
