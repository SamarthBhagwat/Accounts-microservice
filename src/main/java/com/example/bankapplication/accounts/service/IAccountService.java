package com.example.bankapplication.accounts.service;

import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.entity.Customer;

import java.util.Optional;

public interface IAccountService {
    void createAccount(Customer customer);

    CustomerDto fetchAccountDetails(String mobileNumber);
}
