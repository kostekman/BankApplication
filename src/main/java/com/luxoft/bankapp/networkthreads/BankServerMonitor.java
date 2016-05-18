package com.luxoft.bankapp.networkthreads;

import com.luxoft.bankapp.loggers.BankAppLogger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

/**
 * Created by AKoscinski on 2016-04-26.
 */
class BankServerMonitor implements Runnable {
    final private AtomicInteger counter;

    public BankServerMonitor(AtomicInteger counter){
        this.counter = counter;
    }


    @Override
    public void run() {
        while(true) {
            System.out.println("Number of current threads: " + counter.get());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                BankAppLogger.log(Level.SEVERE, "EX " + e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }
}
