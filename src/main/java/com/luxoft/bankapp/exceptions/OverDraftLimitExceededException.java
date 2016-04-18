package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.CheckingAccount;

public class OverDraftLimitExceededException extends NotEnoughFundsException {
	
	private static final long serialVersionUID = 1L;
	private CheckingAccount account;
	float possibleWithdrawal;

	public OverDraftLimitExceededException(float amount, CheckingAccount account) {
		super(amount);
		this.account = account;
		this.possibleWithdrawal = account.getBalance() + account.getOverdraft();
		// TODO Auto-generated constructor stub
	}

	public CheckingAccount getAccount() {
		return account;
	}

	public float getPossibleWithdrawal() {
		return possibleWithdrawal;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Checking account\n");
		sb.append("Possible withdrawal: ");
		sb.append(((Float) possibleWithdrawal).toString());
		return sb.toString();
	}
}
