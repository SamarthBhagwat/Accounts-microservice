package com.example.bankapplication.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Account extends BaseEntity{
    @Id
    private String accountNumber;
    private int customerId;
    private String accountType;
    private String branchAddress;
}
