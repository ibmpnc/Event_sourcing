package com.example.bank.model;

import java.math.BigDecimal;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

//@Value
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountDTO {

	private  BigDecimal initialBalance;
	private  String name;
	private  String address;
	private  String accountType;
	
}
