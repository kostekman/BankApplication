package com.luxoft.bankapp.loggers;

import java.util.logging.*;

/**
 * Created by AKoscinski on 2016-05-10.
 */
public class BankAppLogger {
    private static Logger exceptionLogger;
    static{
        exceptionLogger = Logger.getLogger(ExceptionFilter.class.getName());
        exceptionLogger.setLevel(Level.SEVERE);
        try {
            Handler handler = new FileHandler("src\\main\\resources\\ExceptionLog.log");
            exceptionLogger.addHandler(handler);
            exceptionLogger.setFilter(new ExceptionFilter());
            handler.setFormatter(new SimpleFormatter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Logger connectionLogger;
    static{
        connectionLogger = Logger.getLogger(ConnectionFilter.class.getName());
        connectionLogger.setLevel(Level.INFO);
        try {
            Handler handler = new FileHandler("src\\main\\resources\\ConnectionLog.log");
            connectionLogger.addHandler(handler);
            connectionLogger.setFilter(new ConnectionFilter());
            handler.setFormatter(new SimpleFormatter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Logger databaseLogger;
    static{
        databaseLogger = Logger.getLogger(DBfilter.class.getName());
        databaseLogger.setLevel(Level.INFO);
        try {
            Handler handler = new FileHandler("src\\main\\resources\\DatabaseLog.log");
            databaseLogger.addHandler(handler);
            databaseLogger.setFilter(new DBfilter());
            handler.setFormatter(new SimpleFormatter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(Level level, String message){
        exceptionLogger.log(level, message);
        connectionLogger.log(level, message);
        databaseLogger.log(level, message);
    }

    public static void log(Level level, String message, Exception e){
        exceptionLogger.log(level, message, e);
        connectionLogger.log(level, message, e);
        databaseLogger.log(level, message, e);
    }

}
