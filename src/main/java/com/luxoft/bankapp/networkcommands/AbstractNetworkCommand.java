package com.luxoft.bankapp.networkcommands;

import com.luxoft.bankapp.appinterface.Command;
import com.luxoft.bankapp.model.Bank;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Adam on 22.04.2016.
 */
public class AbstractNetworkCommand implements NetworkCommand {
    ObjectInputStream in;
    ObjectOutputStream out;

    public AbstractNetworkCommand(ObjectInputStream in, ObjectOutputStream out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() {

    }

    @Override
    public void printCommandInfo() {

    }
}
