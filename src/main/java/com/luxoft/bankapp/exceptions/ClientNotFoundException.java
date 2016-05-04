package com.luxoft.bankapp.exceptions;

/**
 * Created by AKoscinski on 2016-04-29.
 */
public class ClientNotFoundException extends Exception{

    public ClientNotFoundException(String name) {
        super("name:" + name);
    }
}
