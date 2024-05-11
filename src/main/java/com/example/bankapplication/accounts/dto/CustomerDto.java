package com.example.bankapplication.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Customer schema"
)
@Data
@Valid
public class CustomerDto {

    @Schema(
            description = "Name of customer"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 20, message = "Name should be between 5 and 20 characters")
    private String name;

    @Schema(
            description = "Email of customer"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email address should be in valid email format")
    private String email;

    @Schema(
            description = "Mobile number of customer"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;

    @Schema(
            description = "Account info of customer"
    )
    private AccountDto accountInfo;
}
