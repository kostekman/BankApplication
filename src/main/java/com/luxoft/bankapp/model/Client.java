package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.Report;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Client implements Report{
	
	private static int nextID = 1;
	
	private String name;
	private int ID;
	private String city;
	private Gender gender;
	private String phoneNumber;
	private String email;
	private Set<Account> accounts;
	private Account activeAccount = null;
	private float initialOverdraft;
	
	public Client(){
		this.name = "";
	}
	
	public Client(String name, String city, Gender gender, String email, String phoneNumber, float initialOverdraft) {
		this.name = name;
		this.city = city;
		this.gender = gender;
		this.initialOverdraft = initialOverdraft;
		this.accounts = new HashSet<>();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.ID = nextID++;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Client client = (Client) o;

		return name != null ? name.equals(client.name) : client.name == null;

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	public int getID() {
		return ID;
	}

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public void addAccount(Account account){
		if(activeAccount == null){
			activeAccount = account;
		}
		accounts.add(account);
	}
	
	public float getBalance() {
		return activeAccount.getBalance();
	}

	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClientSalutation(){
		return gender.getSalutation();
	}

	public float getInitialOverdraft() {
		return initialOverdraft;
	}

	public void setInitialOverdraft(float initialOverdraft) {
		this.initialOverdraft = initialOverdraft;
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	@Override
	public void printReport() {
		System.out.println(this.toString());	
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nName: " + this.getClientSalutation() + " " + this.getName());
		sb.append("\nGender: " + gender.toString());
		sb.append("\n\nAccounts");
		for(Account a : accounts){
			if(a == this.activeAccount)
				sb.append("\nActive Account:");
			sb.append(a.toString());
		}
		sb.append("\nInitialOverdraft: " + Float.valueOf(this.initialOverdraft).toString());
		
		return sb.toString();
	}

}
