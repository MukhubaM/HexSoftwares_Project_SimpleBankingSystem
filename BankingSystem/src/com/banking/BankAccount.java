package com.bankingapp;

public class BankAccount {
    private double balance;
    private String pin;

    public BankAccount(String pin, double initialBalance) {
        this.pin = pin;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
