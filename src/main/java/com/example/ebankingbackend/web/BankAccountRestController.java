package com.example.ebankingbackend.web;

import com.example.ebankingbackend.dtos.AccountHistoryDTO;
import com.example.ebankingbackend.dtos.AccountOperationDTO;
import com.example.ebankingbackend.dtos.BankAccountDTO;
import com.example.ebankingbackend.exception.BankAccountNoFoundException;
import com.example.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNoFoundException {
        return bankAccountService.getBankAccount(accountId);

    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccount(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);

    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId , @RequestParam(name="page",defaultValue = "0") int page , @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNoFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);

    }
    @PostMapping ("/accounts/saveOperation")
    public AccountOperationDTO SaveOperation(@RequestBody AccountOperationDTO accountOperationDTO){
        System.out.println( "***************" + accountOperationDTO);
        return bankAccountService.saveOperation(accountOperationDTO);
    }


}
