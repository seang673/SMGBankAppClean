package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String customerId, double balance) {
        super(customerId, balance);
        this.interestRate = 0.02;
    }

    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount < 100) return false;
        setBalance(getBalance() - amount);
        return true;
    }

    @Override
    public String getType() {
        return "savings";
    }
}
