package com.luxoft.bankapp.networkthreads;

import com.luxoft.bankapp.model.Bank;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import static com.luxoft.bankapp.networkservice.ATMServerService.ATMService;
import static com.luxoft.bankapp.networkservice.RemoteOfficeServerService.RemoteOfficeService;

/**
 * Created by AKoscinski on 2016-04-26.
 */
public class ServerThread implements Runnable {
    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;
    private Bank bank;

    public static AtomicInteger getCounterOfClients() {
        return counterOfClients;
    }

    private static AtomicInteger counterOfClients = new AtomicInteger(0);

    public ServerThread(Socket connection, Bank bank) {
        this.connection = connection;
        this.bank = bank;
        counterOfClients.incrementAndGet();
    }

    public void run() {

        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            do {
                try {
                    message = (String) in.readObject();
                    if (message.equals("ATM")) {
                        ATMService(in, out, bank);
                    } else if (message.equals("OFFICE")) {
                        RemoteOfficeService(in, out, bank);
                    }
                    message = (String) in.readObject();
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
            counterOfClients.decrementAndGet();
        } catch (EOFException e){
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
