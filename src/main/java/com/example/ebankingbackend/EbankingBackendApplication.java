package com.example.ebankingbackend;

import com.example.ebankingbackend.entities.*;
import com.example.ebankingbackend.enums.AccountStatus;
import com.example.ebankingbackend.enums.OperationType;
import com.example.ebankingbackend.exception.BalanceNotSufficientException;
import com.example.ebankingbackend.exception.BankAccountNoFoundException;
import com.example.ebankingbackend.exception.CustomerNoFoundException;
import com.example.ebankingbackend.repositories.AccountOperationRepository;
import com.example.ebankingbackend.repositories.BankAccountRepository;
import com.example.ebankingbackend.repositories.CustomerRepository;
import com.example.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Fatima" , "Aicha" , "Sanae").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+ "@gmail.com");
                bankAccountService.saveCustomer(customer);

            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000 , 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000 , 5.5, customer.getId());
                    List<BankAccount> bankAccounts = bankAccountService.bankAccountList();
                    for (BankAccount bankAccount:bankAccounts){
                            for(int i=0;i<5;i++){
                                bankAccountService.credit(bankAccount.getId(),1000+Math.random()*12000,"Credit");
                                bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"Debit");
                            };
                    }

                }catch (CustomerNoFoundException e){
                    e.printStackTrace();
                } catch (BankAccountNoFoundException e) {
                    throw new RuntimeException(e);
                } catch (BalanceNotSufficientException e) {
                    throw new RuntimeException(e);
                }

            });
        };
    };
    // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Fatima" , "Aicha" , "Sanae").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+ "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setId(UUID.randomUUID().toString());
                        currentAccount.setBalance(Math.random()*8000);
                        currentAccount.setCreatedAt(new Date());
                        currentAccount.setStatus(AccountStatus.CREATED);
                        currentAccount.setCustomer(customer);
                        currentAccount.setOverDraft(9000);
                        bankAccountRepository.save(currentAccount);

                        SavingAccount savingAccount = new SavingAccount();
                        savingAccount.setId(UUID.randomUUID().toString());
                        savingAccount.setBalance(Math.random()*8000);
                        savingAccount.setCreatedAt(new Date());
                        savingAccount.setStatus(AccountStatus.CREATED);
                        savingAccount.setCustomer(customer);
                        savingAccount.setInterestRate(5.5);
                        bankAccountRepository.save(savingAccount);


                    }
                    );

            bankAccountRepository.findAll().forEach(acc ->{
                for (int i= 0 ; i< 5 ; i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setBankAccount(acc);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setType(Math.random() > 0.5? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setAmount(Math.random()*1200);
                    accountOperationRepository.save(accountOperation);
                }
            });



        };
    }

}
