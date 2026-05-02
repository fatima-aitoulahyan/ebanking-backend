package com.example.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private List<AccountOperationDTO> accountOperationDTOS;
    private double balance;
    private String accountId;
    private int currentPage;
    private int totalPages;
    private int pageSize;

}
