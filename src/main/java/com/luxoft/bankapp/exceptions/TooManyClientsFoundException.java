package com.luxoft.bankapp.exceptions;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class TooManyClientsFoundException extends Exception {
    public TooManyClientsFoundException(int counter, String name) {
        super(counter + "clients with that name found with name: " + name);
    }
}
