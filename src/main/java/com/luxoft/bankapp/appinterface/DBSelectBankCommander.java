package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.scanner.BankScanner;

/**
 * Created by AKoscinski on 2016-05-04.
 */
class DBSelectBankCommander {
    public static Bank getBank(){
        System.out.println("Please provide the name of the bank: ");
        String bankname = BankScanner.getScanner().nextLine();
        return Bank.getBankByName(bankname);
    }
}
