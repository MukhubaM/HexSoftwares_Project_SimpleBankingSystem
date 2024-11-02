package com.banking;

public class Customer {
    private String name;
    private String id;
    private BankAccount account;

    public Customer(String name, String id, String accountNumber) {
        this.name = name;
        this.id = id;
        this.account = new BankAccount(accountNumber);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public BankAccount getAccount() {
        return account;
    }
}