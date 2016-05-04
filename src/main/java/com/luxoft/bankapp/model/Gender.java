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

    private String salutation;
    private boolean value;

    Gender(boolean value) {
        if(value == true){
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
