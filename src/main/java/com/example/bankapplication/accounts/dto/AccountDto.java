package com.example.bankapplication.accounts.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String accountNumber;
    private int customerId;
    private String accountType;
    private String branchAddress;
}
