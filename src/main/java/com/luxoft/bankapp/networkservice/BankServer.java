package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.luxoft.bankapp.networkservice.ATMServerService.*;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankServer {
    private Bank bank = new Bank();
    private ServerSocket providerSocket;
    private Socket connection = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;

    void run() {
        try {
            // 1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from "
                    + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    message = (String) in.readObject();
                    if(message.equals("ATM")) {
                        ATMService(in, out, bank);
                    }
                    else if(message.equals("OFFICE")){
                        
                    }
                    message = (String) in.readObject();
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                } catch (BankException e) {
                    sendMessage(e.getMessage());
                }
            } while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankServer server = new BankServer();
        while (true) {
            server.run();
        }
    }
}
