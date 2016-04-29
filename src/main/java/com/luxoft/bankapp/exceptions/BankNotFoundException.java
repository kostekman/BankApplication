package com.luxoft.bankapp.exceptions;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class BankNotFoundException extends Exception {
    public BankNotFoundException(String message) {
        super(message);
    }
}
