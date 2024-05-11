package com.example.bankapplication.accounts.mapper;

import com.example.bankapplication.accounts.dto.AccountDto;
import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.entity.Account;
import com.example.bankapplication.accounts.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer mapCustomerDtoToCustomerEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public CustomerDto mapCustomerToCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
}
