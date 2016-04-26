package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;

public abstract class AbstractCommand implements Command {


    private BankService bankService;
    private static Client activeClient = null;
    private boolean correctData = false;

    public AbstractCommand(BankService bankService) {
        this.bankService = bankService;
    }

    public BankService getBankService() {
        return bankService;
    }

    public static Client getActiveClient() {
        return activeClient;
    }

    public static void setActiveClient(Client activeClient) {
        AbstractCommand.activeClient = activeClient;
    }

    public boolean isCorrectData() {
        return correctData;
    }

    public void setCorrectData(boolean correctData) {
        this.correctData = correctData;
    }

}
