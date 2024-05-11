package com.example.bankapplication.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Account schema"
)
@Data
public class AccountDto {

    @Schema(
            description = "Account number of account"
    )
    private Long accountNumber;

    @Schema(
            description = "Account type of account"
    )
    private String accountType;

    @Schema(
            description = "Branch address of account"
    )
    private String branchAddress;
}
