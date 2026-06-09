package com.example.demo.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public abstract class Account {

    @Id
    private String accId;

    private int accountNumber;
    private String customerId;
    private double balance;

    public Account() {
    }

    public Account(String customerId, double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public abstract boolean withdraw(double amount);

    public abstract String getType();
}

