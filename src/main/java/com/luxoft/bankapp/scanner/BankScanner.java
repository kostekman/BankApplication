package com.luxoft.bankapp.scanner;

import java.util.Scanner;

public class BankScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
