package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class CheckingsAccount extends Account {
    private double overdraftLimit;

    public CheckingsAccount(String customerId, double balance) {
        super(customerId, balance);
        this.overdraftLimit = 500;
    }


    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount < -overdraftLimit) return false;
        setBalance(getBalance() - amount);
        return true;
    }

    @Override
    public String getType() {
        return "checking";
    }
}
