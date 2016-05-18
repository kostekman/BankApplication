package com.luxoft.bankapp.exceptions;

public class NotEnoughFundsException extends BankException {

    private static final long serialVersionUID = 1L;
    private final float amount;

    public NotEnoughFundsException(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public String toString() {
        return Float.valueOf(amount).toString();
    }
}
