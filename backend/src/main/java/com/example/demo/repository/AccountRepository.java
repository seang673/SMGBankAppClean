package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String>{
    Optional<Account> findById(String accId);
    Optional<Account> findByCustomerId(String customerId);
    List<Account> findAll();

}
