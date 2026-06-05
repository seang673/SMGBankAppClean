package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> getPremiumCustomers() {
        return customerRepository.findByIsPremium(true);
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, String name) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(name);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    public Account deposit(String customerId, double amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = accountRepository.findByCustomerId(customerId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found for customer"));
        account.deposit(amount);
        return accountRepository.save(account);
    }

    public boolean withdraw(String customerId, double amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = accountRepository.findByCustomerId(customerId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found for customer"));
        boolean ok = account.withdraw(amount);
        if (ok) accountRepository.save(account);
        return ok;
    }
}

