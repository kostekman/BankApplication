package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.loggers.BankAppLogger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

/**
 * Created by Adam on 21.04.2016.
 */
public class MessageSender {
    public static void sendMessage(final Object msg, ObjectOutputStream out) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            BankAppLogger.getLogger().log(Level.SEVERE, ioException.getMessage(), ioException);
            ioException.printStackTrace();
        }
    }
}
