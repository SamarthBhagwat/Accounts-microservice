package com.example.bankapplication.accounts.service.impl;

import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.entity.Account;
import com.example.bankapplication.accounts.entity.Customer;
import com.example.bankapplication.accounts.exception.CustomerAlreadyExistsException;
import com.example.bankapplication.accounts.mapper.CustomerMapper;
import com.example.bankapplication.accounts.repository.AccountRepository;
import com.example.bankapplication.accounts.repository.CustomerRepository;
import com.example.bankapplication.accounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void createAccount(Customer customer) {
        String email = customer.getEmail();
        Optional<Customer> dbCustomer = customerRepository.findByEmail(email);
        if(dbCustomer.isPresent()){
            throw new CustomerAlreadyExistsException(String.format("Customer with email: %s already exists" , email));
        }
        Customer createdCustomer = customerRepository.save(customer);
        if(createdCustomer.getCustomerId() > 0){
            Account account = createDefaultAccountObject(createdCustomer.getCustomerId());
            accountRepository.save(account);
        }
    }

    private static Account createDefaultAccountObject(Long customerId){
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType("SAVINGS");
        account.setBranchAddress("NB-56, AB Type Colony, Sarni");
        return account;
    }

    private static Long generateAccountNumber(){
        return 1000000000L + new Random().nextInt(1000000000);
    }
}
