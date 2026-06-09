package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.CheckingsAccount;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerService
 * These are example tests showing core patterns for testing service methods
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private CounterService counterService;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        // Create a test customer
        testCustomer = new Customer();
        testCustomer.setCustId("1L");
        testCustomer.setName("Jane Smith");
        testCustomer.setPremium(false);

        // Create a test account for this customer
        testAccount = new CheckingsAccount(testCustomer.getCustId(), 5000.0);
    }

    /**
     * EXAMPLE 1: Testing getCustomer() - SUCCESSFUL RETRIEVAL
     *
     * This demonstrates:
     * - Simple mock setup and return
     * - Basic assertion on retrieved object
     * - Verifying repository call
     */
    @Test
    void testGetCustomer_Success() {
        // GIVEN: Mock the repository to return our test customer
        when(customerRepo.findById("1L")).thenReturn(Optional.of(testCustomer));

        // WHEN: Call the service method
        Customer result = customerService.getCustomer("1L");

        // THEN: Verify the customer was returned correctly
        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());

        assertEquals(1L, result.getCustId());

        // AND: Verify the repository was called
        verify(customerRepo, times(1)).findById("1L");
    }

    /**
     * EXAMPLE 2: Testing createCustomer() - CREATE NEW CUSTOMER
     *
     * This demonstrates:
     * - Testing object creation logic
     * - Mocking the save operation
     * - Verifying that save was called with correct data
     * - Using ArgumentCaptor pattern (shown in comments)
     */
    @Test
    void testCreateCustomer_Success() {
        // GIVEN: A new customer that will be returned after save
        Customer newCustomer = new Customer("Bob Johnson");
        newCustomer.setCustId("5L");  // Simulate DB-assigned ID

        when(customerRepo.save(any(Customer.class))).thenReturn(newCustomer);

        // WHEN: Create a new customer
        Customer result = customerService.createCustomer(newCustomer);

        // THEN: Verify the created customer
        assertNotNull(result);
        assertEquals("Bob Johnson", result.getName());

        assertEquals("5L", result.getCustId());

        // AND: Verify save was called exactly once
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    /**
     * EXAMPLE 3: Testing withdraw() - COMPLEX MULTI-STEP OPERATION
     *
     * This demonstrates:
     * - Multiple mock setups for dependent operations
     * - Testing business logic across multiple repositories
     * - Verifying multiple interactions
     */

    /**
     * EXAMPLE 4: Testing getPremiumCustomers() - LIST OPERATIONS
     *
     * This demonstrates:
     * - Testing methods that return collections
     * - Testing filtering/search logic
     * - Verifying list contents
     */
    @Test
    void testGetPremiumCustomers_ReturnsPremiumOnly() {
        // GIVEN: Multiple customers, some premium and some not
        Customer premiumCustomer1 = new Customer("Alice Premium");
        premiumCustomer1.setPremium(true);

        Customer premiumCustomer2 = new Customer("BobPremium");
        premiumCustomer2.setPremium(true);

        when(customerRepo.findByIsPremium(true))
            .thenReturn(List.of(premiumCustomer1, premiumCustomer2));

        // WHEN: Get all premium customers
        List<Customer> result = customerService.getPremiumCustomers();

        // THEN: Should return only 2 premium customers
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Customer::isPremium));

        // AND: Verify the correct query was used
        verify(customerRepo, times(1)).findByIsPremium(true);
    }
}
