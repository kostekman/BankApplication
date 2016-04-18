package com.luxoft.bankapp.networkservice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankClient {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

    void run() {
        try {
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to localhost in port 2004");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3: Communicating with the server
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("server>" + message);
                    sendMessage("Hi my server");
                    message = "bye";
                    sendMessage(message);
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankClient client = new BankClient();
        client.run();
    }

}
