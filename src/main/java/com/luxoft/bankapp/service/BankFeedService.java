package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankFeedService {

    private Map<String, String> clientMap = new HashMap<>();
    private Map<String, String> accountMap = new HashMap<>();
    private Bank activeBank;

    public BankFeedService(Bank activeBank) {
        this.activeBank = activeBank;
    }

    public Map<String, String> getAccountMap() {
        return accountMap;
    }

    public Map<String, String> getClientMap() {
        return clientMap;
    }

    public void loadFeed(String pathToFile) {
        try (FileReader fr = new FileReader(pathToFile);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
