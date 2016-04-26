package com.luxoft.bankapp.networkcommands;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static com.luxoft.bankapp.scanner.StringChecker.checkName;

/**
 * Created by Adam on 22.04.2016.
 */
public class GetClientInfoNetworkCommand extends AbstractNetworkCommand {
    boolean isCorrectData = false;

    public GetClientInfoNetworkCommand(ObjectInputStream in, ObjectOutputStream out) {
        super(in, out);
    }

    @Override
    public void execute() {
        sendMessage("CLIENTINFO", out);

        String name = "";
        while (!isCorrectData) {
            System.out.println("Please type the name of the client as follows: \"name surname\".");
            name = BankScanner.getScanner().nextLine();
            isCorrectData = checkName(name);
            if (!isCorrectData) {
                System.out.println("The name you entered is incorrect");
            }

        }
        isCorrectData = false;

        try {
            sendMessage(name, out);
            Client client = ((Client) in.readObject());
            System.out.println(client.getReport());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            try {
                String message = (String) in.readObject();
                System.out.println(message);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command gets client info from the server");
    }
}
