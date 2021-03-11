package com.example.bank.event;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Value;


@Value
public class WithdrawEvent {

	
	private final UUID id;
	private final BigDecimal withdrawAmount;
}
