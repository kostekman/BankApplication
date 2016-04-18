package com.luxoft.bankapp.model;

public enum Gender {

	MALE("Mr.") {
		public void work() {
			System.out.println("I work like an engineer");
		}
	}, FEMALE("Mrs.") {
		public void work() {
			System.out.println("I work like a doctor");
		}
	};
	
	private String salutation; 

	private Gender(String salutation) {
		this.salutation = salutation;
	}
	
	public String getSalutation() {
		return salutation;
	}
	
	public abstract void work();
}
