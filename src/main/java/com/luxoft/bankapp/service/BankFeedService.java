package com.luxoft.bankapp.service;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.model.Bank;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-18.
 */
class BankFeedService {

    private final Map<String, String> clientMap = new HashMap<>();
    private final Map<String, String> accountMap = new HashMap<>();

    public BankFeedService(Bank activeBank) {
        Bank activeBank1 = activeBank;
    }

    public Map<String, String> getAccountMap() {
        return accountMap;
    }

    public Map<String, String> getClientMap() {
        return clientMap;
    }

    public void loadFeed() {
        try (FileReader fr = new FileReader(com.luxoft.bankapp.service.BankApplication.fileName);
             LineNumberReader lnr = new LineNumberReader(fr)) {

            String line;
            while ((line = lnr.readLine()) != null) {
                String[] lineSplitted = line.split(";|=");
                if (lineSplitted[0].equals("name")) {
                    for (int i = 0; i < lineSplitted.length; i++) {
                        clientMap.put(lineSplitted[i], lineSplitted[++i]);
                    }
                } else if (lineSplitted[0].equals("accounttype")) {
                    for (int i = 0; i < lineSplitted.length; i++) {
                        accountMap.put(lineSplitted[i], lineSplitted[++i]);
                    }
                }

            }

        } catch (IOException e) {
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
