package com.example.bank.command;

import java.math.BigDecimal;
import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.example.bank.model.TransactonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawCommand {

	
	@TargetAggregateIdentifier
    private UUID accountId;
	private BigDecimal withdrawAmount;
	private TransactonType transactionType;
}