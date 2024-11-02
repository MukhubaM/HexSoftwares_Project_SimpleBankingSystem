package com.bankingapp;

import java.util.ArrayList;
import java.util.List;

public class BankingSystem {
    private List<Customer> customers;

    public BankingSystem() {
        customers = new ArrayList<>();
    }

    public void addCustomer(String name, double initialBalance, String pin, String email) {
        customers.add(new Customer(name, pin, initialBalance, email));
    }

    public Customer getCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
}
