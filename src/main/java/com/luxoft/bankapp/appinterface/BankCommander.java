package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.scanner.BankScanner;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class BankCommander {
    private static BankService bankService = new BankServiceImpl();
    private static Map<String, Command> commandMap;
    private static Bank activeBank;

    static {
        commandMap = new HashMap<>();
        commandMap.put("0", new FindClientCommand(bankService));
        commandMap.put("1", new AddAccountCommand(bankService));
        commandMap.put("2", new GetAccountsCommand(bankService));
        commandMap.put("3", new WithdrawCommand(bankService));
        commandMap.put("4", new DepositCommand(bankService));
        commandMap.put("5", new TransferCommand(bankService));
        commandMap.put("6", new AddClientCommand(bankService));
        commandMap.put("7", new Command() { // 7 - Exit Command
            public void execute(Bank bank) {
                BankScanner.closeScanner();
                System.exit(0);
            }

            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }

    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    public void removeCommand(String name) {
        commandMap.remove(name);
    }

    public static void main(String args[]) {

        while (true) {
            activeBank = DBSelectBankCommander.getBank();
            System.out.println("\n");
            for (String name : commandMap.keySet()) {
                commandMap.get(name).printCommandInfo();
            }
            String commandString = BankScanner.getScanner().nextLine();
            if (commandMap.keySet().contains(commandString)) {
                commandMap.get(commandString).execute(activeBank);
            } else {
                System.out.println("No such command in the system");
            }

        }
    }
}
