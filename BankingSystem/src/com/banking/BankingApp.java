package com.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BankingApp extends JFrame {
    private BankingSystem bankingSystem;
    private Customer loggedInCustomer;

    public BankingApp() {
        bankingSystem = new BankingSystem();
        setTitle("Simple Banking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JButton createAccountButton = new JButton("Create Account");
        JButton loginButton = new JButton("Login");
        JButton retrievePinButton = new JButton("Retrieve PIN");
        JButton exitButton = new JButton("Exit");

        createAccountButton.addActionListener(e -> openCreateAccountWindow());
        loginButton.addActionListener(e -> openLoginWindow());
        retrievePinButton.addActionListener(e -> openRetrievePinWindow());
        exitButton.addActionListener(e -> System.exit(0));

        add(createAccountButton);
        add(loginButton);
        add(retrievePinButton);
        add(exitButton);
    }

    private void openCreateAccountWindow() {
        JDialog dialog = new JDialog(this, "Create Account", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField initialBalanceField = new JTextField();
        JTextField pinField = new JPasswordField();
        JTextField emailField = new JTextField();

        dialog.add(new JLabel("Customer Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Initial Deposit:"));
        dialog.add(initialBalanceField);
        dialog.add(new JLabel("Set PIN:"));
        dialog.add(pinField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            double initialBalance;

            try {
                initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
                if (initialBalance < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid initial deposit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pin = pinField.getText().trim();
            String email = emailField.getText().trim();
            if (pin.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "PIN and email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bankingSystem.addCustomer(name, initialBalance, pin, email);
            JOptionPane.showMessageDialog(dialog, "Account created for " + name + "!");
            dialog.dispose();
        });

        dialog.add(createButton);
        dialog.setVisible(true);
    }

    private void openLoginWindow() {
        JDialog dialog = new JDialog(this, "Login", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField pinField = new JPasswordField();

        dialog.add(new JLabel("Customer Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("PIN:"));
        dialog.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pin = pinField.getText().trim();

            Customer customer = bankingSystem.getCustomer(name);
            if (customer != null && customer.getAccount().getPin().equals(pin)) {
                loggedInCustomer = customer;
                JOptionPane.showMessageDialog(dialog, "Login successful!");
                dialog.dispose();
                openMainBankingWindow();
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(loginButton);
        dialog.setVisible(true);
    }

    private void openMainBankingWindow() {
        if (loggedInCustomer == null) return;

        JDialog dialog = new JDialog(this, "Banking Operations", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 1));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Check Balance");
        JButton logoutButton = new JButton("Logout");

        depositButton.addActionListener(e -> openDepositWindow());
        withdrawButton.addActionListener(e -> openWithdrawWindow());
        balanceButton.addActionListener(e -> checkBalance());
        logoutButton.addActionListener(e -> {
            loggedInCustomer = null;
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Logged out successfully.");
        });

        dialog.add(depositButton);
        dialog.add(withdrawButton);
        dialog.add(balanceButton);
        dialog.add(logoutButton);
        dialog.setVisible(true);
    }

    private void openDepositWindow() {
        if (loggedInCustomer == null) return;

        JDialog dialog = new JDialog(this, "Deposit Money", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField amountField = new JTextField();

        dialog.add(new JLabel("Amount to Deposit:"));
        dialog.add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> {
            double amount;

            try {
                amount = Double.parseDouble(amountField.getText().trim());
                if (amount <= 0) throw new NumberFormatException();
                loggedInCustomer.getAccount().deposit(amount);
                JOptionPane.showMessageDialog(dialog, "Deposit successful!");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(depositButton);
        dialog.setVisible(true);
    }

    private void openWithdrawWindow() {
        if (loggedInCustomer == null) return;

        JDialog dialog = new JDialog(this, "Withdraw Money", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField amountField = new JTextField();

        dialog.add(new JLabel("Amount to Withdraw:"));
        dialog.add(amountField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> {
            double amount;

            try {
                amount = Double.parseDouble(amountField.getText().trim());
                if (amount <= 0) throw new NumberFormatException();

                boolean success = loggedInCustomer.getAccount().withdraw(amount);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Withdrawal successful!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(withdrawButton);
        dialog.setVisible(true);
    }

    private void checkBalance() {
        if (loggedInCustomer == null) return;

        double balance = loggedInCustomer.getAccount().getBalance();
        JOptionPane.showMessageDialog(this, "Your current balance is: $" + balance);
    }

    private void openRetrievePinWindow() {
        JDialog dialog = new JDialog(this, "Retrieve PIN", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        dialog.add(new JLabel("Customer Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        JButton retrieveButton = new JButton("Retrieve PIN");
        retrieveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            Customer customer = bankingSystem.getCustomer(name);
            if (customer != null && customer.getEmail().equals(email)) {
                String pin = customer.getAccount().getPin();
                // Simulate sending an email
                JOptionPane.showMessageDialog(dialog, "Your PIN has been sent to your email: " + email);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid customer name or email.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(retrieveButton);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankingApp app = new BankingApp();
            app.setVisible(true);
        });
    }
}
