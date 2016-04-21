package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;

/**
 * Created by Adam on 21.04.2016.
 */
public class ATMServerService {


    public static void ATMService(ObjectInputStream in, ObjectOutputStream out, Bank bank) throws IOException, ClassNotFoundException, BankException {
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
        }while (client == null);
        String operation = (String) in.readObject();
        float amount = (Float) in.readObject();
        if(operation.equals("1")){
            client.getActiveAccount().withdraw(amount);
        }
        else{
            client.getActiveAccount().deposit(amount);
        }
        sendMessage("Transaction successful", out);
        sendMessage("bye", out);
    }

}
