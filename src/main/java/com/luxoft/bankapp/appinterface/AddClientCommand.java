package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

import static com.luxoft.bankapp.scanner.StringChecker.*;

public class AddClientCommand extends AbstractCommand implements Command {

    public AddClientCommand(BankService bankService) {
        super(bankService);
    }

    @Override
    public void execute(Bank bank) {
        String name = "";
        while (!isCorrectData()) {
            System.out.println("Please type the name of the new client as follows: \"name surname\".");
            name = BankScanner.getScanner().nextLine();
            setCorrectData(checkName(name));
            if (!isCorrectData()) {
                System.out.println("The name you entered is incorrect");
            } else if (bank.getClientNameMap().get(name) != null) {
                System.out.println("Client already exists in system");
                setCorrectData(false);
            }

        }
        setCorrectData(false);

        String city = "";
        while (!isCorrectData()) {
            System.out.println("Please type the city of the new client.");
            city = BankScanner.getScanner().nextLine();
            setCorrectData(checkCity(city));
            if (!isCorrectData()) {
                System.out.println("The data you entered is incorrect");
            }

        }
        setCorrectData(false);


        String email = "";
        while (!isCorrectData()) {
            System.out.println("Please type an e-mail of the client");
            email = BankScanner.getScanner().nextLine();
            setCorrectData(checkEmail(email));
            if (!isCorrectData()) {
                System.out.println("The email you entered is incorrect");
            }

        }

        setCorrectData(false);

        String phoneNumber = "";
        while (!isCorrectData()) {
            System.out.println("Please type a phonenumber of the client");
            phoneNumber = BankScanner.getScanner().nextLine();
            setCorrectData(checkPhoneNumber(phoneNumber));
            if (!isCorrectData()) {
                System.out.println("The phonenumber you entered is incorrect");
            }

        }

        setCorrectData(false);

        String overdraft = "";
        while (!isCorrectData()) {
            System.out.println("Please provide how much is overdraft.");
            overdraft = BankScanner.getScanner().nextLine();
            setCorrectData(checkNumber(overdraft));
            if (!isCorrectData()) {
                System.out.println("The number you entered is incorrect");
            }

        }
        setCorrectData(false);

        String gender = "";
        while (!isCorrectData()) {
            System.out.println("Please provide gener male(0) or female(1)");
            gender = BankScanner.getScanner().nextLine();
            if (!(gender.equals("1") || gender.equals("0"))) {
                System.out.println("You have entered invalid data");
            } else {
                setCorrectData(true);
            }
        }
        setCorrectData(false);

        Gender enumGender;
        if (gender.equals("0")) {
            enumGender = Gender.MALE;
        } else {
            enumGender = Gender.FEMALE;
        }
        Client newClient = new Client(name, city, enumGender, email, phoneNumber, Float.valueOf(overdraft));
        getBankService().addClient(bank, newClient);
        setActiveClient(newClient);
        System.out.println("Client successfully created");

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command creates a client basing on provided data");

    }

}
