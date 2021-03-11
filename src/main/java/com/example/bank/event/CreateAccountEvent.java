package com.example.bank.event;

import java.math.BigDecimal;
import java.util.UUID;



import lombok.Data;


@Data
public class CreateAccountEvent {

	
	private final UUID id;
    private final BigDecimal initialBalance;
    private final String name;
}
