package com.luxoft.bankapp.loggers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AKoscinski on 2016-05-10.
 */
public class CurrentDateAndTime {
    public static String getCurrentDateAndTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
