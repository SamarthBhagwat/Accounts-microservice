package com.example.bankapplication.accounts.controller;

import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.dto.ResponseDto;
import com.example.bankapplication.accounts.entity.Customer;
import com.example.bankapplication.accounts.mapper.CustomerMapper;
import com.example.bankapplication.accounts.service.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@Validated
public class AccountController {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    IAccountService accountService;

    @GetMapping("/")
    public String sayHello(){
        return "Hello world";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto){
        Customer customer = customerMapper.mapCustomerDtoToCustomerEntity(customerDto);
        accountService.createAccount(customer);
        ResponseDto successResponse = new ResponseDto("201", "Customer created successfully");
        return new ResponseEntity<>(successResponse, HttpStatusCode.valueOf(201));
    }
}
