package com.example.bank.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {
    @Id
    private UUID accountNumber;
    private String name;
    private BigDecimal balance;
    private Instant lastupdatedTimestamp;
}