package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.luxoft.bankapp.scanner.BankScanner.getScanner;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class ATMClient {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

    void run() {
        try {
            System.out.println("Select host: ");
            String serverAddress = getScanner().nextLine();
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(serverAddress, 2004);
            System.out.println("Connected to " + serverAddress +" in port 2004");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3: Communicating with the server
            do {
                try {
                    sendMessage("ATM");
                    String client;
                    do {
                        System.out.println("Select client: (name surname)");
                        client = BankScanner.getScanner().nextLine();
                        sendMessage(client);
                        message = (String) in.readObject();
                        System.out.println(message);
                    }while(message.equals("Client not found"));
                    String operation;
                    do {
                        System.out.println("Select deposit(0) or withdrawal(1)");
                        operation = BankScanner.getScanner().nextLine();
                    }while(!(operation.equals("0") || operation.equals("1")));
                    sendMessage(operation);
                    Float amount = BankScanner.getScanner().nextFloat();
                    sendMessage(amount);
                    message = (String)in.readObject();
                    System.out.println(message);
                    message = (String)in.readObject();

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

    void sendMessage(final Object msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        ATMClient client = new ATMClient();
        client.run();
    }

}