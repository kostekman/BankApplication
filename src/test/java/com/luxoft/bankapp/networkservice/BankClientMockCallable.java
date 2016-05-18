package com.luxoft.bankapp.networkservice;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;

/**
 * Created by AKoscinski on 2016-04-26.
 */
class BankClientMockCallable implements Callable {
    private Socket requestSocket;

    @Override
    public Long call() throws Exception {
        long startTime = System.nanoTime();
        try {
            requestSocket = new Socket("localhost", 2004);
            ObjectOutputStream out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            sendMessage("ATM", out);
            Thread.sleep(100);
            sendMessage("John Dorian", out);
            Thread.sleep(100);
            sendMessage("1", out);
            Thread.sleep(100);
            sendMessage(1f, out);
            Thread.sleep(100);
            sendMessage("bye", out);
            Thread.sleep(100);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000000;
    }
}
