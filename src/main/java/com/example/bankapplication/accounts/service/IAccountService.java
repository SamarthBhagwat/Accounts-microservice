package com.example.bankapplication.accounts.service;

import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.entity.Customer;


public interface IAccountService {
    void createAccount(Customer customer);

    CustomerDto fetchAccountDetails(String mobileNumber);

    boolean updateAccountDetails(CustomerDto customerDto);

    boolean deleteAccountDetails(String mobileNumber);
}
