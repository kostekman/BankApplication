package com.luxoft.bankapp.networkservice;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Adam on 21.04.2016.
 */
public class MessageSender {
    static void sendMessage(final Object msg, ObjectOutputStream out) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
