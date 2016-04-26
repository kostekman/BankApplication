package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static org.junit.Assert.assertEquals;

/**
 * Created by AKoscinski on 2016-04-26.
 */
public class ATMClientTest {
    private static final float AMOUNTOFCASH = 1000f;
    private static final int AMOUNTOFTRANSACTIONS = 10;
    private static final String NAME = "John Dorian";
    private static ExecutorService executorServerService = Executors.newSingleThreadExecutor();

    @BeforeClass
    public static void initialize() {
        Socket requestSocket;
        ObjectOutputStream out;
        try {
            requestSocket = new Socket("localhost", 2004);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            sendMessage("OFFICE", out);
            Thread.sleep(100);
            sendMessage("ADDCLIENT", out);
            Thread.sleep(100);
            Client client = new Client(NAME, "Cracow", Gender.MALE, "kos@kos.pl", "737737737", 300f);
            sendMessage(client, out);
            Thread.sleep(100);
            sendMessage("4", out);
            sendMessage("bye", out);
            Thread.sleep(100);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            requestSocket = new Socket("localhost", 2004);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            sendMessage("ATM", out);
            Thread.sleep(100);
            sendMessage(NAME, out);
            Thread.sleep(100);
            sendMessage("0", out);
            Thread.sleep(100);
            sendMessage(AMOUNTOFCASH, out);
            Thread.sleep(100);
            sendMessage("bye", out);
            Thread.sleep(100);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTransactions() {
        List<Thread> listOfMocks = new ArrayList<>(AMOUNTOFTRANSACTIONS);
        for (int i = 0; i < AMOUNTOFTRANSACTIONS; i++) {
            listOfMocks.add(new Thread(new BankClientMock()));
        }
        for (Thread mock : listOfMocks) {
            mock.start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            for (Thread mock : listOfMocks) {
                mock.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Float finalAmount = getAmount(NAME);

        assertEquals("Amounts are not equal", finalAmount, AMOUNTOFCASH - (float) AMOUNTOFTRANSACTIONS, 0.5);
    }

    private Float getAmount(String name) {
        try {
            Socket requestSocket;
            ObjectOutputStream out;
            ObjectInputStream in;
            requestSocket = new Socket("localhost", 2004);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());
            out.flush();
            sendMessage("OFFICE", out);
            Thread.sleep(100);
            sendMessage("CLIENTINFO", out);
            Thread.sleep(100);
            sendMessage(name, out);
            Client client = (Client) in.readObject();
            sendMessage("4", out);
            sendMessage("bye", out);
            in.close();
            out.close();

            return client.getActiveAccount().getBalance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Float.MIN_VALUE;
    }
}