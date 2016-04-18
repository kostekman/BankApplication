package com.luxoft.bankapp.appinterface;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCommand implements Command {
	
	private Pattern namePattern = Pattern.compile("^[A-Z][a-zA-Z]* [a-zA-z]+([ '-][a-zA-Z]+)*$");
	private Matcher nameMatcher;

	private Pattern numberPattern = Pattern.compile("[0-9]+?(.[0-9]+)");
	private Matcher numberMatcher;
	
	private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&�*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	private Matcher emailMatcher;
	
	private Pattern phoneNumberPattern = Pattern.compile("^(\\+[0-9]{2})?[0-9]{9}$");
	private Matcher phoneNumberMatcher;

	private  Pattern cityPattern = Pattern.compile("[A-Z][a-z]*( [A-Z][a-z]*)*");
	private Matcher cityMatcher;

	private BankService bankService;
	private static Client activeClient = null; 
	private boolean correctData = false;
	
	public Matcher getNameMatcher() {
		return nameMatcher;
	}

	public void setNameMatcher(Matcher nameMatcher) {
		this.nameMatcher = nameMatcher;
	}

	public Pattern getNamePattern() {
		return namePattern;
	}
	
	public Pattern getNumberPattern() {
		return numberPattern;
	}

	public void setNumberMatcher(Matcher matcher) {
		this.numberMatcher = matcher;
	}

	public Matcher getNumberMatcher() {
		return numberMatcher;
	}
	
	public Matcher getEmailMatcher() {
		return emailMatcher;
	}

	public void setEmailMatcher(Matcher emailMatcher) {
		this.emailMatcher = emailMatcher;
	}

	public Pattern getEmailPattern() {
		return emailPattern;
	}
	
	public Matcher getPhoneNumberMatcher() {
		return phoneNumberMatcher;
	}

	public void setPhoneNumberMatcher(Matcher phoneNumberMatcher) {
		this.phoneNumberMatcher = phoneNumberMatcher;
	}

	public Pattern getPhoneNumberPattern() {
		return phoneNumberPattern;
	}

	public Pattern getCityPattern() {
		return cityPattern;
	}

	public Matcher getCityMatcher() {
		return cityMatcher;
	}

	public void setCityMatcher(Matcher cityMatcher) {
		this.cityMatcher = cityMatcher;
	}

	public AbstractCommand(BankService bankService){
		this.bankService = bankService;
	}
	
	public BankService getBankService() {
		return bankService;
	}

	public static Client getActiveClient() {
		return activeClient;
	}

	public static void setActiveClient(Client activeClient) {
		AbstractCommand.activeClient = activeClient;
	}
	
	public boolean isCorrectData() {
		return correctData;
	}

	public void setCorrectData(boolean correctData) {
		this.correctData = correctData;
	}

}
