package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

import static com.luxoft.bankapp.scanner.StringChecker.checkName;

public class FindClientCommand extends AbstractCommand implements Command {

    public FindClientCommand(BankService bankService) {
        super(bankService);
    }

    @Override
    public void execute(Bank bank) {
        String name = "";
        while (!isCorrectData()) {
            System.out.println("Please type the name of the client as follows: \"name surname\".");
            name = BankScanner.getScanner().nextLine();
            setCorrectData(checkName(name));
            if (!isCorrectData()) {
                System.out.println("The name you entered is incorrect");
            }

        }
        setCorrectData(false);

        Client client = this.getBankService().findClient(bank, name);
        if (client != null) {
            setActiveClient(client);
            System.out.println("Current client is: " + client.getName() + " ID: " + Integer.valueOf(client.getID()).toString());
        } else {
            System.out.println("This client is not registered in the system, please try again");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command finds client by the name, setting him as active.");

    }

}
