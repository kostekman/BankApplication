package com.luxoft.bankapp.model;

public enum Gender {

    MALE(1) {
        public void work() {
            System.out.println("I work like an engineer");
        }
    }, FEMALE(0) {
        public void work() {
            System.out.println("I work like a doctor");
        }
    };

    private String salutation;
    private int value;

    Gender(int value) {
        if(value == 1){
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

    public int toInt(){
        return value;
    }

    public abstract void work();
}
