package com.luxoft.bankapp.model;

public enum Gender {

    MALE(true) {
        public void work() {
            System.out.println("I work like an engineer");
        }
    }, FEMALE(false) {
        public void work() {
            System.out.println("I work like a doctor");
        }
    };

    private final String salutation;
    private final boolean value;

    Gender(boolean value) {
        if(value){
            this.salutation = "Mr. ";
        }
        else {
            this.salutation = "Mrs. ";
        }
        this.value = value;
    }

    public String getSalutation() {
        return salutation;
    }

    public boolean toBoolean(){
        return value;
    }

    public abstract void work();
}
