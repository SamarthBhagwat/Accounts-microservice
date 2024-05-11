package com.example.bankapplication.accounts.controller;

import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.dto.ResponseDto;
import com.example.bankapplication.accounts.entity.Customer;
import com.example.bankapplication.accounts.mapper.CustomerMapper;
import com.example.bankapplication.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        ResponseDto successResponse = new ResponseDto(HttpStatus.CREATED.toString(), "Customer created successfully");
        return new ResponseEntity<>(successResponse, HttpStatusCode.valueOf(201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                               String mobileNumber){
        CustomerDto customerDto = accountService.fetchAccountDetails(mobileNumber);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }
}
