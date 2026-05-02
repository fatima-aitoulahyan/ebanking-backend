package com.example.ebankingbackend;

import com.example.ebankingbackend.dtos.BankAccountDTO;
import com.example.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.example.ebankingbackend.dtos.CustomerDTO;
import com.example.ebankingbackend.dtos.SavingBankAccountDTO;
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
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+ "@gmail.com");
                bankAccountService.saveCustomer(customer);

            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000 , 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000 , 5.5, customer.getId());


                }catch (CustomerNoFoundException e){
                    e.printStackTrace();
                }

            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for(int i=0;i<5;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,1000+Math.random()*12000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                };
            }
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



        };
    }

}
