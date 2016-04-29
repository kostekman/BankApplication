package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.BankException;

public abstract class AbstractAccount implements Account {
    private float balance;
    private int id;
    private String accountType;

    public AbstractAccount(float balance) {
        this.balance = balance;
    }

    public AbstractAccount() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAccount that = (AbstractAccount) o;

        if (id != that.id) return false;
        return accountType.equals(that.accountType);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result;
        return result;
    }

    @Override
    public void printReport() {

    }

    @Override
    public String getReport() {
        return this.toString();
    }

    @Override
    public int decimalValue() {
        return Math.round(balance);
    }

    @Override
    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public float getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    synchronized public void deposit(float x) {
        balance += x;

    }

    @Override
    synchronized public void withdraw(float x) throws BankException {
        balance -= x;

    }

}
