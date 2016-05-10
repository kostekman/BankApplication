package com.luxoft.bankapp.networkcommands;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static com.luxoft.bankapp.scanner.StringChecker.*;

/**
 * Created by Adam on 22.04.2016.
 */
public class AddClientNetworkCommand extends AbstractNetworkCommand {
    boolean isCorrectData = false;

    public AddClientNetworkCommand(ObjectInputStream in, ObjectOutputStream out) {
        super(in, out);
    }

    @Override
    public void execute() {
        sendMessage("ADDCLIENT", out);

        String name = "";
        while (!isCorrectData) {
            System.out.println("Please type the name of the new client as follows: \"name surname\".");
            name = BankScanner.getScanner().nextLine();
            isCorrectData = (checkName(name));
            if (!isCorrectData) {
                System.out.println("The name you entered is incorrect");
            }

        }
        isCorrectData = (false);

        String city = "";
        while (!isCorrectData) {
            System.out.println("Please type the city of the new client.");
            city = BankScanner.getScanner().nextLine();
            isCorrectData = (checkCity(city));
            if (!isCorrectData) {
                System.out.println("The data you entered is incorrect");
            }

        }
        isCorrectData = (false);


        String email = "";
        while (!isCorrectData) {
            System.out.println("Please type an e-mail of the client");
            email = BankScanner.getScanner().nextLine();
            isCorrectData = (checkEmail(email));
            if (!isCorrectData) {
                System.out.println("The email you entered is incorrect");
            }

        }

        isCorrectData = (false);

        String phoneNumber = "";
        while (!isCorrectData) {
            System.out.println("Please type a phonenumber of the client");
            phoneNumber = BankScanner.getScanner().nextLine();
            isCorrectData = (checkPhoneNumber(phoneNumber));
            if (!isCorrectData) {
                System.out.println("The phonenumber you entered is incorrect");
            }

        }

        isCorrectData = (false);

        String overdraft = "";
        while (!isCorrectData) {
            System.out.println("Please provide how much is overdraft.");
            overdraft = BankScanner.getScanner().nextLine();
            isCorrectData = (checkNumber(overdraft));
            if (!isCorrectData) {
                System.out.println("The number you entered is incorrect");
            }

        }
        isCorrectData = (false);

        String gender = "";
        while (!isCorrectData) {
            System.out.println("Please provide gener male(0) or female(1)");
            gender = BankScanner.getScanner().nextLine();
            if (!(gender.equals("1") || gender.equals("0"))) {
                System.out.println("You have entered invalid data");
            } else {
                isCorrectData = (true);
            }
        }
        isCorrectData = (false);

        Gender enumGender;
        if (gender.equals("0")) {
            enumGender = Gender.MALE;
        } else {
            enumGender = Gender.FEMALE;
        }
        Client newClient = new Client(name, city, enumGender, email, phoneNumber, Float.valueOf(overdraft));

        sendMessage(newClient, out);

        try {
            String message = (String) in.readObject();
            System.out.println(message);
        } catch (IOException | ClassNotFoundException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command adds new client to the bank");
    }
}
