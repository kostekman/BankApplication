package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static com.luxoft.bankapp.scanner.BankScanner.getScanner;

/**
 * Created by Adam on 21.04.2016.
 */
public class BankRemoteOfficeClient {
    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;

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
                    sendMessage("OFFICE", out);
                    String client;


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

    public static void main(final String args[]) {
        BankRemoteOfficeClient client = new BankRemoteOfficeClient();
        client.run();
    }

}

