package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankFeedServiceTests {
    private BankFeedService bankFeedService;
    @BeforeClass
    public static void initializeFile(){
        try(FileWriter fr = new FileWriter("testData.txt");
            PrintWriter pw = new PrintWriter(fr)){

            pw.write("name=John Kubrick;city=Cracow;gender=m;email=email@email.com;phonenumber=737737737;initialoverdraft=300.0");
            pw.write("accounttype=c;balance=100;overdraft=50;name=John Kubrick");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void removeFile(){
        try {
            Files.delete(Paths.get("testData.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initializeBankFeedService(){
        Bank bank = Mockito.mock(Bank.class);
        bankFeedService  = new BankFeedService(bank);
    }

    @Test
    public void testIfFileIsLoaded(){
        assertTrue("File is not properly loaded",bankFeedService.loadFeed("testData.txt"));
    }

    @Test
    public void testParsingAndLoadingToClientMap(){
        bankFeedService.loadFeed("testData.txt");
        assertEquals("Client info is not loaded properly", "John Kubrick", bankFeedService.getClientMap().get("name"));
    }

    @Test
    public void testParsingAndLoadingToAccountMap(){
        bankFeedService.loadFeed("testData");
        assertEquals("Accout info is not loaded properly", "John Kubrick", bankFeedService.getAccountMap().get("name"));
    }

}
