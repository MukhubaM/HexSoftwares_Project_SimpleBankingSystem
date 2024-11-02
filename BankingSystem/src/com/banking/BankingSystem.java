package com.banking;

import java.util.HashMap;
import java.util.Map;

public class BankingSystem {
    private Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        if (!customers.containsKey(customer.getId())) {
            customers.put(customer.getId(), customer);
        } else {
            throw new IllegalArgumentException("Customer ID already exists.");
        }
    }

    public Customer findCustomer(String customerId) {
        return customers.get(customerId);
    }
}