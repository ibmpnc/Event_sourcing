package com.example.bank.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;



import com.example.bank.model.TransactonType;

import lombok.Value;


@Value
public class TransactionEvent {

	
	 private final UUID id;
	 private final BigDecimal amount;
	 private Instant transactionTs;
	 private  String transactionType;
}
