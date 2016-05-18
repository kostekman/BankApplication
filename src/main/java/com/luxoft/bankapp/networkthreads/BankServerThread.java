package com.luxoft.bankapp.networkthreads;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-18.
 */
public class BankServerThread implements Runnable {
    private final int POOL_SIZE = 20;
    private final Bank bank = new Bank("test");
    private final boolean running = true;
    private final ExecutorService executorServerService = Executors.newFixedThreadPool(POOL_SIZE);
    private final ExecutorService executorMonitorService = Executors.newSingleThreadExecutor();

    public void run() {
        try {
            // 1. creating a server socket
            ServerSocket providerSocket = new ServerSocket(2004, 10);
            executorMonitorService.execute(new BankServerMonitor(ServerThread.getCounterOfClients()));
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            while (running) {
                Socket connection = providerSocket.accept();
                System.out.println("Connection received from "
                        + connection.getInetAddress().getHostName());
                executorServerService.execute(new ServerThread(connection, bank));
            }

        } catch (IOException ioException) {
            BankAppLogger.log(Level.SEVERE, ioException.getMessage(), ioException);
            ioException.printStackTrace();
        } finally {
            executorServerService.shutdown();
        }
    }

    public static void main(final String args[]) {
        BankServerThread server = new BankServerThread();
            server.run();

    }
}
