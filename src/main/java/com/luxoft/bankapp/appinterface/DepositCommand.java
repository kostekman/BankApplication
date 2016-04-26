package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;

import static com.luxoft.bankapp.scanner.StringChecker.checkName;

public class DepositCommand extends AbstractAccountBalanceModifierCommand implements Command {

    public DepositCommand(BankService bankService) {
        super(bankService);
    }

    @Override
    public void execute(Bank bank) {
        if (getActiveClient().getActiveAccount() == null) {
            System.out.println("This client has no account yet, please create an account");
            return;
        }
        String amount = "";
        while (!isCorrectData()) {
            System.out.println("Please provide how much money you want to deposit.");
            amount = BankScanner.getScanner().nextLine();
            setCorrectData(checkName(amount));
            if (!isCorrectData()) {
                System.out.println("The number you entered is incorrect");
            }

        }
        setCorrectData(false);
        depositOnAccount(amount);

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposits given amount of money to active account");

    }

}
