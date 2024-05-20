package com.example.bankapplication.accounts.controller;

import com.example.bankapplication.accounts.dto.ContactDetailsDto;
import com.example.bankapplication.accounts.dto.CustomerDto;
import com.example.bankapplication.accounts.dto.ErrorResponseDto;
import com.example.bankapplication.accounts.dto.ResponseDto;
import com.example.bankapplication.accounts.entity.Customer;
import com.example.bankapplication.accounts.mapper.CustomerMapper;
import com.example.bankapplication.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs for Accounts in Eazybank to create, read, fetch and delete accounts details"
)
@RestController
@RequestMapping(value = "/api")
@Validated
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService iAccountService){
        this.accountService = iAccountService;
    }

    @Value("${build.info}")
    private String buildInfo;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private ContactDetailsDto contactDetailsDto;

    @Operation(
            summary = "Test api",
            description = "Test endpoint to check status of server"
    )
    @GetMapping("/")
    public String sayHello(){
        return "Hello world";
    }


    @Operation(
            summary = "Create customer and account",
            description = "REST API to create new Customer and Account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status created"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto){
        Customer customer = customerMapper.mapCustomerDtoToCustomerEntity(customerDto);
        accountService.createAccount(customer);
        ResponseDto successResponse = new ResponseDto(HttpStatus.CREATED.toString(), "Customer created successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch customer and account details",
            description = "REST API to fetch customer and account details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                               String mobileNumber){
        CustomerDto customerDto = accountService.fetchAccountDetails(mobileNumber);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }


    @Operation(
            summary = "Update customer and accounts details",
            description = "REST API to update customer and accounts details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something went wrong",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto){
        boolean isUpdated = accountService.updateAccountDetails(customerDto);
        if(isUpdated){
            ResponseDto successResponse = new ResponseDto(HttpStatus.OK.toString(),
                    "Account information updated Successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal Server error") ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @Operation(
            summary = "Delete customer and account",
            description = "REST API to delete customer and account details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something went wrong",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                            String mobileNumber){
        boolean isDeleted = accountService.deleteAccountDetails(mobileNumber);
        if(isDeleted){
            ResponseDto successResponse = new ResponseDto(HttpStatus.OK.toString(),
                    "Account information deleted Successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal Server error") ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Operation(
            summary = "Fetch build version",
            description = "REST API to fetch build version of accounts microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> fetchBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildInfo);
    }


    @Operation(
            summary = "Fetch java version which accounts microservice is using",
            description = "REST API to fetch java version of accounts microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> fetchJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_VERSION"));
    }


    @Operation(
            summary = "Fetch contact details",
            description = "REST API to fetch contact details of accounts microservice maintainer"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<ContactDetailsDto> fetchContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(contactDetailsDto);
    }
}
