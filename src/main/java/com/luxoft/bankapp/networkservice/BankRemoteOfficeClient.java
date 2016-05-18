package com.luxoft.bankapp.networkservice;

import com.luxoft.bankapp.loggers.BankAppLogger;
import com.luxoft.bankapp.networkcommands.*;
import com.luxoft.bankapp.scanner.BankScanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;
import static com.luxoft.bankapp.scanner.BankScanner.getScanner;

/**
 * Created by Adam on 21.04.2016.
 */
public class BankRemoteOfficeClient {
    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean exit = false;

    private final Map<String, NetworkCommand> commandMap = new HashMap<>();


    private void run() {
        try {
            System.out.println("Select host: ");
            String serverAddress = getScanner().nextLine();
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(serverAddress, 2004);
            System.out.println("Connected to " + serverAddress + " in port 2004");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            initializeCommandMap(in, out);
            // 3: Communicating with the server
            sendMessage("OFFICE", out);
            do {
                System.out.println("\n");
                Object[] keys = commandMap.keySet().toArray();
                Arrays.sort(keys);
                for (Object name : keys) {
                    commandMap.get(name).printCommandInfo();
                }
                String commandString = BankScanner.getScanner().nextLine();
                if (commandMap.keySet().contains(commandString)) {
                    commandMap.get(commandString).execute();
                } else {
                    System.out.println("No such command in the system");
                }
            } while (!exit);
        } catch (UnknownHostException unknownHost) {
            BankAppLogger.log(Level.SEVERE, unknownHost.getMessage(), unknownHost);
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            BankAppLogger.log(Level.SEVERE, ioException.getMessage(), ioException);
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                BankAppLogger.log(Level.SEVERE, ioException.getMessage(), ioException);
                ioException.printStackTrace();
            }
        }
    }

    private void initializeCommandMap(ObjectInputStream in, final ObjectOutputStream out) {
        commandMap.put("0", new GetBankStatisticsNetworkCommand(in, out));
        commandMap.put("1", new GetClientInfoNetworkCommand(in, out));
        commandMap.put("2", new AddClientNetworkCommand(in, out));
        commandMap.put("3", new RemoveClientNetworkCommand(in, out));
        commandMap.put("4", new NetworkCommand() { // 7 - Exit Command
            public void execute() {
                BankScanner.closeScanner();
                sendMessage("bye", out);
                exit = true;
            }

            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }

    public static void main(final String args[]) {
        BankRemoteOfficeClient client = new BankRemoteOfficeClient();
        client.run();
    }

}

