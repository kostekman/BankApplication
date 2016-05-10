package com.luxoft.bankapp.loggers;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by AKoscinski on 2016-05-10.
 */
public class DBfilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        String msg = record.getMessage();
        if(msg.startsWith("DB")){
            return true;
        }
        return false;
    }
}
