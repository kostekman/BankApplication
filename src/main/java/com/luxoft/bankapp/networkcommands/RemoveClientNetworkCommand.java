package com.luxoft.bankapp.networkcommands;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static com.luxoft.bankapp.scanner.StringChecker.checkName;

/**
 * Created by Adam on 22.04.2016.
 */
public class RemoveClientNetworkCommand extends AbstractNetworkCommand {
    private boolean isCorrectData = false;

    public RemoveClientNetworkCommand(ObjectInputStream in, ObjectOutputStream out) {
        super(in, out);
    }

    @Override
    public void execute() {
        sendMessage("REMOVECLIENT", out);

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
            String report = ((String) in.readObject());
            System.out.println(report);
        } catch (IOException | ClassNotFoundException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command removes client from the server");
    }
}