package com.bankingapp;

public class Customer {
    private String name;
    private BankAccount account;
    private String email;

    public Customer(String name, String pin, double initialBalance, String email) {
        this.name = name;
        this.account = new BankAccount(pin, initialBalance);
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public BankAccount getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }
}
