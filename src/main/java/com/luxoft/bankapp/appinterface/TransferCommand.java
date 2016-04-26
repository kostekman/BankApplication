package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

import static com.luxoft.bankapp.scanner.StringChecker.checkName;
import static com.luxoft.bankapp.scanner.StringChecker.checkNumber;

public class TransferCommand extends AbstractAccountBalanceModifierCommand implements Command {

    Client clientToTransfer;

    public TransferCommand(BankService bankService) {
        super(bankService);
    }

    @Override
    public void execute(Bank bank) {
        if (getActiveClient().getActiveAccount() == null) {
            System.out.println("This client has no account yet, please create an account");
            return;
        }
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
        clientToTransfer = this.getBankService().findClient(bank, name);
        if (clientToTransfer.getActiveAccount() == null) {
            System.out.println("This client has no account yet, please create an account");
            return;
        }
        if (clientToTransfer != null) {
            String amount = "";
            while (!isCorrectData()) {
                System.out.println("Please provide how much money you want to transfer.");
                amount = BankScanner.getScanner().nextLine();
                setCorrectData(checkNumber(amount));
                if (!isCorrectData()) {
                    System.out.println("The number you entered is incorrect");
                }

            }

            setCorrectData(false);
            if (withdrawFromAccount(amount)) {
                depositOnAccount(clientToTransfer, amount);
            } else {
                System.out.println("The transfer was not possible and aborted");
            }
        } else {
            System.out.println("This client is not registered in the system, please try again");

        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command looks for a second client, and transfers given amount money to its active account");

    }

}
