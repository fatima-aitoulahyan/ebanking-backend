package com.example.ebankingbackend.web;

import com.example.ebankingbackend.dtos.CustomerDTO;
import com.example.ebankingbackend.entities.Customer;
import com.example.ebankingbackend.exception.CustomerNoFoundException;
import com.example.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return  bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id" )Long customerID) throws CustomerNoFoundException {
        return bankAccountService.getcustomer(customerID);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);

    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable(name = "customerId") Long customerId , @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deletCustomer(id);
    }
}
