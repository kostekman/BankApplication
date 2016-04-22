package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Adam on 22.04.2016.
 */
public class AbstractNetworkCommand implements Command{
    ObjectInputStream in;
    ObjectOutputStream out;

    @Override
    public void execute(Bank bank) {

    }

    @Override
    public void printCommandInfo() {

    }
}
