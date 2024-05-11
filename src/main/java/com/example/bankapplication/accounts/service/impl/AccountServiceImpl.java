package com.example.bankapplication.accounts.service.impl;

import com.example.bankapplication.accounts.dto.AccountDto;
import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.entity.Account;
import com.example.bankapplication.accounts.entity.Customer;
import com.example.bankapplication.accounts.exception.CustomerAlreadyExistsException;
import com.example.bankapplication.accounts.exception.ResourceNotFoundException;
import com.example.bankapplication.accounts.mapper.AccountMapper;
import com.example.bankapplication.accounts.mapper.CustomerMapper;
import com.example.bankapplication.accounts.repository.AccountRepository;
import com.example.bankapplication.accounts.repository.CustomerRepository;
import com.example.bankapplication.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    AccountMapper accountMapper;

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

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = customerMapper.mapCustomerToCustomerDto(customer);
        AccountDto accountDto = accountMapper.mapAccountToAccountDto(account);
        customerDto.setAccountInfo(accountDto);
        return customerDto;
    }

    @Override
    public boolean updateAccountDetails(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountInfo();
        if(accountDto != null){
            Account account = accountRepository.findById(accountDto.getAccountNumber().toString()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountId", accountDto.getAccountNumber().toString())
            );
            account = accountMapper.mapAccountDtoToAccount(accountDto, account);
            accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );
            customer = customerMapper.mapCustomerDtoToCustomerEntity(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
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
