package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;

/**
 * Created by Adam on 21.04.2016.
 */
public class ATMServerService {


    public static void ATMService(ObjectInputStream in, ObjectOutputStream out, Bank bank) throws IOException, ClassNotFoundException {
        long startTime = System.nanoTime();
        String message;
        BankService bankService = new BankServiceImpl();

        Client client;
        do {
            message = (String) in.readObject();
            client = bankService.findClient(bank, message);
            if (client != null) {
                sendMessage("Client: " + client.getName() + " found.", out);
            } else {
                sendMessage("Client not found", out);
            }
        } while (client == null);
        String operation = (String) in.readObject();
        float amount = (Float) in.readObject();
        try {
            if (operation.equals("1")) {
                bankService.withdrawFromAccount(client.getActiveAccount(), amount);
            } else if (operation.equals("0")) {
                bankService.depositOnAccount(client.getActiveAccount(), amount);
            }
            sendMessage("Transaction successful", out);
            long endTime = System.nanoTime();
            BankAppLogger.getLogger().log(Level.INFO, "Connection duration: " + (endTime - startTime)/1000000);
        } catch (BankException e) {
            BankAppLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
            sendMessage(e.toString(), out);
        }
    }

}
