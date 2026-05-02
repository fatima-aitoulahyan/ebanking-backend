package com.example.ebankingbackend.services;

import com.example.ebankingbackend.dtos.CustomerDTO;
import com.example.ebankingbackend.entities.*;
import com.example.ebankingbackend.enums.OperationType;
import com.example.ebankingbackend.exception.BalanceNotSufficientException;
import com.example.ebankingbackend.exception.BankAccountNoFoundException;
import com.example.ebankingbackend.exception.CustomerNoFoundException;
import com.example.ebankingbackend.mappers.BankAccountMapperImpl;
import com.example.ebankingbackend.repositories.AccountOperationRepository;
import com.example.ebankingbackend.repositories.BankAccountRepository;
import com.example.ebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    //Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Saving new Customer");
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNoFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        if(customer==null)
            throw new CustomerNoFoundException("Customer not found");
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);

        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNoFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNoFoundException("Customer not found");
        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);

        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);

        return savedBankAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNoFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null)
            throw new BankAccountNoFoundException("Account not found");
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNoFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNoFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountTdDestination, double amount) throws BalanceNotSufficientException, BankAccountNoFoundException {

        debit(accountIdSource,amount,"transfer to "+accountTdDestination);
        credit(accountTdDestination,amount,"Transfer from " +accountIdSource);
    }
    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getcustomer(Long customerId) throws CustomerNoFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNoFoundException("customer not found"));
        return dtoMapper.fromCustomer(customer);

    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deletCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }
}
