package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;


import java.util.*;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findById(String customerId);
    Optional<Customer> findByName(String name);
    List<Customer> findByIsPremium(boolean isPremium);

}



