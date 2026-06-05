package com.example.demo.service;
import com.example.demo.model.Account;
import com.example.demo.model.CheckingsAccount;
import com.example.demo.model.Customer;
import com.example.demo.model.SavingsAccount;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;

    public AccountService(AccountRepository accountRepo, CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    public Account getAccount(String id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Account createAccount(String customerId, double balance, String type) {
         // Validate customer exists
        customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = switch (type.toLowerCase()) {
            case "checking" -> new CheckingsAccount(customerId, balance);
            case "savings"  -> new SavingsAccount(customerId, balance);
            default -> throw new RuntimeException("Invalid account type");
        };

        return accountRepo.save(account);
    }

    public void deleteAccount(String id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepo.delete(account);
    }

    public Account deposit(String accId, double amount) {
        Account acc = accountRepo.findById(accId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.deposit(amount);
        return accountRepo.save(acc);
    }

    public Account withdraw(String accId, double amount) {
        Account acc = accountRepo.findById(accId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        boolean ok = acc.withdraw(amount);
        if (!ok) {
            throw new RuntimeException("Insufficient funds");
        }

        return accountRepo.save(acc);
    }
}
