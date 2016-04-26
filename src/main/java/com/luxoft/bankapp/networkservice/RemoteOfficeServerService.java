package com.luxoft.bankapp.networkservice;

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
public class RemoteOfficeServerService {

    public static void RemoteOfficeService(ObjectInputStream in, ObjectOutputStream out, Bank bank) {
        String command = "";
        BankService bankService = new BankServiceImpl();
        do {
            try {
                command = (String) in.readObject();
                if (command.equals("BANKINFO")) {
                    sendMessage(bankService.getBankInfo(bank), out);
                } else if (command.equals("CLIENTINFO")) {
                    String name = (String) in.readObject();
                    Client client = bankService.findClient(bank, name);
                    if (client != null) {
                        sendMessage(client, out);
                    } else {
                        sendMessage("Client not found", out);
                    }
                } else if (command.equals("ADDCLIENT")) {
                    Client client = (Client) in.readObject();
                    bankService.addClient(bank, client);
                    sendMessage("Client added", out);
                } else if (command.equals("REMOVECLIENT")) {
                    String name = (String) in.readObject();
                    Client client = bankService.findClient(bank, name);
                    if (client != null) {
                        bankService.removeClient(bank, client);
                        sendMessage("Client removed", out);
                    } else {
                        sendMessage("Client not found", out);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (!command.equals("bye"));

    }
}
