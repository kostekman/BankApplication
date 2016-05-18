package com.luxoft.bankapp.networkservice;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;

/**
 * Created by AKoscinski on 2016-04-26.
 */
public class BankClientMockRunnable implements Runnable {
    private Socket requestSocket;

    @Override
    public void run() {
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
    }

    public static void main(final String args[]) {
        BankClientMockRunnable client = new BankClientMockRunnable();
        client.run();
    }

}
