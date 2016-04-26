package com.luxoft.bankapp.service;

import java.io.Serializable;

/**
 * Created by Adam on 22.04.2016.
 */
public class BankInfo implements Serializable {
    private String bankReport;

    public BankInfo() {
    }

    public String getBankReport() {
        return bankReport;
    }

    public void setBankReport(String bankReport) {
        this.bankReport = bankReport;
    }

}
