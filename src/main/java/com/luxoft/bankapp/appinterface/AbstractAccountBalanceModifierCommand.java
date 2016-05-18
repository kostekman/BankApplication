package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;

abstract class AbstractAccountBalanceModifierCommand extends AbstractCommand {

    AbstractAccountBalanceModifierCommand(BankService bankService) {
        super(bankService);
    }

    boolean withdrawFromAccount(String amount) {
        boolean completed = false;
        try {
            getBankService().withdrawFromAccount(getActiveClient().getActiveAccount(), Float.valueOf(amount));
            completed = true;
            System.out.printf("You have successfully withdrawed %s from the account", amount);
        } catch (BankException e) {
            System.out.println(e.toString());
            //e.printStackTrace();
        }
        return completed;

    }

    void depositOnAccount(String amount) {
        getBankService().depositOnAccount(getActiveClient().getActiveAccount(), Float.valueOf(amount));
        System.out.printf("You have successfully deposited %s on the account", amount);
    }

    void depositOnAccount(Client client, String amount) {
        getBankService().depositOnAccount(client.getActiveAccount(), Float.valueOf(amount));
        System.out.printf("You have successfully deposited %s on the account", amount);
    }

}
