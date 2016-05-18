package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;

interface Command {
    void execute(Bank bank);

    void printCommandInfo();
}
