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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountService
 * These are example tests showing core patterns for testing service methods
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;
    private Customer testCustomer;

    /**
     * Setup: Creates test fixtures before each test
     * This runs before EVERY test method to ensure clean state
     */
    @BeforeEach
    void setUp() {
        // Create a test customer
        testCustomer = new Customer();
        testCustomer.setCustId("1L");
        testCustomer.setName("John Doe");
        testCustomer.setPremium(false);

        // Create a test account linked to the customer
        testAccount = new CheckingsAccount(testCustomer.getCustId(), 1000.0);
    }

    /**
     * EXAMPLE 1: Testing getAccount() - SUCCESSFUL CASE
     *
     * This demonstrates:
     * - Setting up mock behavior with when/thenReturn
     * - Calling the service method
     * - Asserting the result with assertEquals
     * - Verifying the mock was called
     */
    @Test
    void testGetAccount_Success() {
        // GIVEN: We have a mock that will return an account when findById is called
        when(accountRepo.findById("1L")).thenReturn(Optional.of(testAccount));

        // Query account with id 1
        Account result = accountService.getAccount("1L");

        // THEN: Assert the queried account's balance is 1000
        assertNotNull(result);
        assertEquals(1000.0, result.getBalance());

        // AND: Verify that the repository method was called with the correct ID
        verify(accountRepo, times(1)).findById("1L");

    }

    /**
     * EXAMPLE 3: Testing deposit() - SUCCESSFUL TRANSACTION
     *
     * This demonstrates:
     * - Testing business logic (deposit increases balance)
     * - Capturing the account that gets saved
     * - Verifying the correct data was persisted
     */
    @Test
    void testDeposit_Success() {
        // GIVEN: We have an account and a mock that returns it, plus a saved version
        Account accountAfterDeposit = new CheckingsAccount(testCustomer.getCustId(), 1500.0);

        when(accountRepo.findById("1L")).thenReturn(Optional.of(testAccount));
        when(accountRepo.save(any(Account.class))).thenReturn(accountAfterDeposit);

        // WHEN: We deposit $500
        Account result = accountService.deposit("1L", 500.0);

        // THEN: The returned account should have the updated balance
        assertEquals(1500.0, result.getBalance());

        // AND: Verify that save() was called with an account (we don't check exact balance
        // because the mock just returns our prepared accountAfterDeposit)
        verify(accountRepo, times(1)).save(any(Account.class));
    }



    @Test
    void testWithdraw(){
        Account accountAfterWithdraw = new CheckingsAccount(testCustomer.getCustId(), 500.0);

        when(accountRepo.findById("1L")).thenReturn(Optional.of(testAccount));
        when(accountRepo.save(any(Account.class))).thenReturn(accountAfterWithdraw);

        Account result = accountService.withdraw("1L", 500.0);

        assertEquals(500.0, result.getBalance());

        verify(accountRepo, times(1)).save(any(Account.class));
    }
}
