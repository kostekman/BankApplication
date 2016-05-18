package com.luxoft.bankapp.loggers;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by AKoscinski on 2016-05-10.
 */
class DBfilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        String msg = record.getMessage();
        return msg.contains("DB");
    }
}
