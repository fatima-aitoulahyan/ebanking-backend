package com.example.ebankingbackend.services;

import com.example.ebankingbackend.entities.BankAccount;
import com.example.ebankingbackend.entities.CurrentAccount;
import com.example.ebankingbackend.entities.Customer;
import com.example.ebankingbackend.entities.SavingAccount;
import com.example.ebankingbackend.exception.BalanceNotSufficientException;
import com.example.ebankingbackend.exception.BankAccountNoFoundException;
import com.example.ebankingbackend.exception.CustomerNoFoundException;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance , double overDraft , Long customerId) throws CustomerNoFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance , double interestRate , Long customerId) throws CustomerNoFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNoFoundException, BankAccountNoFoundException;
    void debit(String accountId , double amount , String description) throws BankAccountNoFoundException, BalanceNotSufficientException;
    void credit(String accountId , double amount , String description) throws BankAccountNoFoundException;

    void transfer(String accountIdSource , String accountTdDestination,double amount) throws BalanceNotSufficientException, BankAccountNoFoundException;

    List<BankAccount> bankAccountList();
}
