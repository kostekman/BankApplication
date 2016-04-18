package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.service.Report;

import java.io.Serializable;
import java.util.Map;

public interface Account extends Report, Serializable {
    void setBalance(float balance);

    public float getBalance();

    public void deposit(float x);

    public void withdraw(float x) throws BankException;

    public int decimalValue();

    public void parseFeed(Map<String, String> feed);

    public String getAccountType();
}
