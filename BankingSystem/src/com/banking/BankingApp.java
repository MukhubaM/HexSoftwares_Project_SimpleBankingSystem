package com.banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingApp extends JFrame {
    private BankingSystem bankingSystem = new BankingSystem();

    public BankingApp() {
        setTitle("Simple Banking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Header for the application
        JLabel header = new JLabel("Simple Banking System", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(header, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Form Components
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField accountField = new JTextField();
        JTextField amountField = new JTextField();

        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Customer ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Account Number:"));
        formPanel.add(accountField);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Check Balance");

        buttonPanel.add(createAccountButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(balanceButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button Action Listeners
        createAccountButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String accountNumber = accountField.getText();

            try {
                if (!name.isEmpty() && !id.isEmpty() && !accountNumber.isEmpty()) {
                    Customer customer = new Customer(name, id, accountNumber);
                    bankingSystem.addCustomer(customer);
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    clearFields(nameField, idField, accountField, amountField);
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        depositButton.addActionListener(e -> {
            String customerId = idField.getText();
            try {
                double amount = Double.parseDouble(amountField.getText());
                Customer customer = bankingSystem.findCustomer(customerId);

                if (customer != null) {
                    customer.getAccount().deposit(amount);
                    JOptionPane.showMessageDialog(this, "Deposited $" + amount);
                    clearFields(nameField, idField, accountField, amountField);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        withdrawButton.addActionListener(e -> {
            String customerId = idField.getText();
            try {
                double amount = Double.parseDouble(amountField.getText());
                Customer customer = bankingSystem.findCustomer(customerId);

                if (customer != null) {
                    customer.getAccount().withdraw(amount);
                    JOptionPane.showMessageDialog(this, "Withdrew $" + amount);
                    clearFields(nameField, idField, accountField, amountField);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        balanceButton.addActionListener(e -> {
            String customerId = idField.getText();
            Customer customer = bankingSystem.findCustomer(customerId);

            if (customer != null) {
                double balance = customer.getAccount().getBalance();
                JOptionPane.showMessageDialog(this, "Current Balance: $" + balance);
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found.");
            }
        });
    }

    // Method to clear input fields after each action
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingApp().setVisible(true));
    }
}