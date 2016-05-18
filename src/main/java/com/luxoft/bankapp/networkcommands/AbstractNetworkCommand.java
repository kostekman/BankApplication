package com.luxoft.bankapp.networkcommands;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Adam on 22.04.2016.
 */
public class AbstractNetworkCommand implements NetworkCommand {
    final ObjectInputStream in;
    final ObjectOutputStream out;

    AbstractNetworkCommand(ObjectInputStream in, ObjectOutputStream out) {
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
