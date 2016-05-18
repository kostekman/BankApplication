package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.SavingAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class SerializationTest {
    private static final String filePath = "src\\test\\testresources\\testData.data";
    private BankService bankService;

    @BeforeClass
    public static void createFileIfNeeded(){
        File file = new File("src\\test\\testresources");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Before
    public void initialization(){
        bankService = new BankServiceImpl();
    }
    @Test
    public void serializationTest(){
        Client client = new Client("Jan Kowalski", "Krakow", Gender.MALE, "email@email.pl", "919919919", 300.0f);
        client.addAccount(new CheckingAccount(300f, 200f));
        client.addAccount(new SavingAccount(300f));

        bankService.saveClient(client);

        Client newClient = bankService.loadClient();

        assertEquals("Objects are not the same", client, newClient);
        System.out.println(newClient.toString());
    }
    @After
    public void cleanup(){
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
