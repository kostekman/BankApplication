package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankFeedServiceTests {
    private BankFeedService bankFeedService;
    private static final String filePath = "src\\test\\testresources\\testData.txt";

    @BeforeClass
    public static void initializeFile() {
        File file = new File("src\\test\\testresources");
        if (!file.exists()) {
            file.mkdir();
        }

        try (FileWriter fr = new FileWriter(filePath);
             PrintWriter pw = new PrintWriter(fr)) {

            pw.write("name=John Kubrick;city=Cracow;gender=m;email=email@email.com;phonenumber=737737737;initialoverdraft=300.0\n");
            pw.write("accounttype=c;balance=100;overdraft=50;name=John Kubrick\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void removeFile() {
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initializeBankFeedService() {
        Bank bank = Mockito.mock(Bank.class);
        bankFeedService = new BankFeedService(bank);
    }

    @Test
    public void testIfFileIsLoaded() {
        bankFeedService.loadFeed();
    }

    @Test
    public void testParsingAndLoadingToClientMap() {
        bankFeedService.loadFeed();
        assertEquals("Client info is not loaded properly", "John Kubrick", bankFeedService.getClientMap().get("name"));
    }

    @Test
    public void testParsingAndLoadingToAccountMap() {
        bankFeedService.loadFeed();
        assertEquals("Account info is not loaded properly", "John Kubrick", bankFeedService.getAccountMap().get("name"));
    }

}
