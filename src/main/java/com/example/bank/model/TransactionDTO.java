package com.example.bank.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.example.bank.entity.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @JsonProperty(value = "id")
	private String id;
	@JsonProperty(value = "transactionTs")
	private String transactionTs;
	@JsonProperty(value = "transactionType")
	private String transactionType;
	@JsonProperty(value = "amount")
	private BigDecimal amount;
	
	
	
}
