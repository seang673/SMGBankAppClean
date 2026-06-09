package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MigrationService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CounterService counterService;
    private final MongoOperations mongoOperations;

    public MigrationService(CustomerRepository customerRepository,
                            AccountRepository accountRepository,
                            CounterService counterService,
                            MongoOperations mongoOperations) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.counterService = counterService;
        this.mongoOperations = mongoOperations;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runMigrations() {
        int migratedCustomers = backfillCustomerNumbers();
        int migratedAccounts = backfillAccountNumbers();
        System.out.println("=== Migration complete: " + migratedCustomers + " customers, " + migratedAccounts + " accounts assigned sequential IDs ===");
    }

    private int backfillCustomerNumbers() {
        Query query = new Query(Criteria.where("customerNumber").is(0));
        List<Customer> unassigned = mongoOperations.find(query, Customer.class);
        for (Customer customer : unassigned) {
            customer.setCustomerNumber(counterService.getNextSequence("customerNumber"));
            customerRepository.save(customer);
        }
        return unassigned.size();
    }

    private int backfillAccountNumbers() {
        Query query = new Query(Criteria.where("accountNumber").is(0));
        List<Account> unassigned = mongoOperations.find(query, Account.class);
        for (Account account : unassigned) {
            account.setAccountNumber(counterService.getNextSequence("accountNumber"));
            accountRepository.save(account);
        }
        return unassigned.size();
    }
}
