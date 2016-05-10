package com.luxoft.bankapp.datagenarator;

import com.luxoft.bankapp.loggers.BankAppLogger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;

public class DataGenerator {

    static Random r;
    private static String[] names = {"Joey", "Ross", "Chandler", "Adam", "Matt"};
    private static String[] surnames = {"Hamilton", "Perry", "White", "Damon", "Cruise"};

    public static void generateData(String fileName, String csvSeparator, int customersAmount, int accountsPerCustomer) {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {


            r = new Random();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < customersAmount; i++) {
                stringBuilder.append("CLIENT" + csvSeparator);
                stringBuilder.append(names[Math.abs(r.nextInt()) % names.length] + " " + surnames[Math.abs(r.nextInt()) % names.length] + csvSeparator);

                if (r.nextBoolean())
                    stringBuilder.append("0" + csvSeparator);
                else
                    stringBuilder.append("1" + csvSeparator);

                stringBuilder.append(((Float) (Math.abs(r.nextFloat()) * (float) Math.abs(r.nextInt()) % 1000)).toString() + csvSeparator);
                stringBuilder.append("\n");

                for (int j = 0; j < r.nextInt(accountsPerCustomer - 1) + 1; j++) {
                    stringBuilder.append("ACCOUNT" + csvSeparator);
                    if (r.nextBoolean()) {
                        stringBuilder.append("0" + csvSeparator);
                        stringBuilder.append(((Float) (Math.abs(r.nextFloat() * (float) Math.abs(r.nextInt()) % 1000))).toString() + csvSeparator);
                    } else {
                        stringBuilder.append("1" + csvSeparator);
                        stringBuilder.append(((Float) (Math.abs(r.nextFloat() * (float) Math.abs(r.nextInt()) % 1000))).toString() + csvSeparator + ((Float) (Math.abs(r.nextFloat() * (float) Math.abs(r.nextInt()) % 1000))).toString() + csvSeparator);
                    }
                    stringBuilder.append("\n");
                }
            }
            printWriter.println(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
        }
    }

}
