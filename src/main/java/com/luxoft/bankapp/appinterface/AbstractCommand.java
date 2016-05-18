package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;

abstract class AbstractCommand implements Command {


    private final BankService bankService;
    private static Client activeClient = null;
    private boolean correctData = false;

    AbstractCommand(BankService bankService) {
        this.bankService = bankService;
    }

    BankService getBankService() {
        return bankService;
    }

    static Client getActiveClient() {
        return activeClient;
    }

    static void setActiveClient(Client activeClient) {
        AbstractCommand.activeClient = activeClient;
    }

    boolean isCorrectData() {
        return correctData;
    }

    void setCorrectData(boolean correctData) {
        this.correctData = correctData;
    }

}
