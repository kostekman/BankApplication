package com.luxoft.bankapp.networkthreads;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.loggers.CurrentDateAndTime;
import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

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
                try {
                    message = (String) in.readObject();
                    if (message.equals("ATM")) {
                        BankAppLogger.log(Level.INFO, CurrentDateAndTime.getCurrentDateAndTime() + " | " + "ATM connected");
                        ATMService(in, out, bank);
                    } else if (message.equals("OFFICE")) {
                        BankAppLogger.log(Level.INFO, CurrentDateAndTime.getCurrentDateAndTime() + " | " + "OFFICE connected");
                        RemoteOfficeService(in, out, bank);
                    }

                } catch (ClassNotFoundException classnot) {
                    BankAppLogger.log(Level.SEVERE, classnot.getMessage(), classnot);
                    System.err.println("Data received in unknown format");
                }
            counterOfClients.decrementAndGet();
        } catch (IOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
                e.printStackTrace();
            }

        }
    }
}
