package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Counter;

public interface CounterRepository extends MongoRepository<Counter, String> {}

