package com.example.bankapplication.accounts.mapper;

import com.example.bankapplication.accounts.dto.AccountDto;
import com.example.bankapplication.accounts.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto mapAccountToAccountDto(Account account){
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }
}
