package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.service.Report;

import java.io.Serializable;
import java.util.Map;

public interface Account extends Report, Serializable {
    void setBalance(float balance);

    float getBalance();

    void deposit(float x);

    void withdraw(float x) throws BankException;

    void setId(int id);

    int decimalValue();

    void parseFeed(Map<String, String> feed);

    String getAccountType();

    int getId();
}
