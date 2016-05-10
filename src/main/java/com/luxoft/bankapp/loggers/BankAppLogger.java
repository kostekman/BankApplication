package com.luxoft.bankapp.loggers;

import com.luxoft.bankapp.service.BankApplication;

import java.util.logging.*;

/**
 * Created by AKoscinski on 2016-05-10.
 */
public class BankAppLogger {
    private static Logger logger;
    static{
        logger = Logger.getLogger(BankApplication.class.getName());
        logger.setLevel(Level.ALL);
        try {
            Handler handler = new FileHandler("src\\main\\resources\\log.log");
            logger.addHandler(handler);
            handler.setFormatter(new SimpleFormatter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static Logger getLogger() {
        return logger;
    }

}
