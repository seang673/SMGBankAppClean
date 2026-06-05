package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateCustomer;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import jakarta.annotation.Generated;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }
    
    @GetMapping("/premium")
    public ResponseEntity<?> getPremiumCustomers() {
        return ResponseEntity.ok(customerService.getPremiumCustomers());
    }
    @PostMapping("")
    public Customer createCustomer(@RequestBody CreateCustomer request) {
        return customerService.createCustomer(request.getName());
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestParam String name) {
        return ResponseEntity.ok(customerService.updateCustomer(id, name));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted");
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String id, @RequestParam double amount) {
        return ResponseEntity.ok(customerService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String id, @RequestParam double amount) {
        boolean ok = customerService.withdraw(id, amount);
        if (ok) return ResponseEntity.ok("Withdrawal successful");
        else return ResponseEntity.badRequest().body("Withdrawal denied");
    }
}
