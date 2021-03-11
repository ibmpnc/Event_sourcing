package com.example.bank.command;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.example.bank.model.TransactonType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;





public class DepositeCommand extends TransactionCommand{
	
    private final static TransactonType transactionType= TransactonType.DEPOSITE;
}
