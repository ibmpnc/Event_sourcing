package com.example.bank.service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.example.bank.command.CreateAccountCommand;
import com.example.bank.command.DepositeCommand;
import com.example.bank.command.TransactionCommand;
import com.example.bank.command.WithdrawCommand;
import com.example.bank.entity.BankAccount;
import com.example.bank.model.Constants;
import com.example.bank.model.CreateBankAccountDTO;
import com.example.bank.model.MoneyDTO;
import com.example.bank.model.TransactonType;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.bank.service.ServiceUtil.formatUuid;

@Service
@AllArgsConstructor
@Slf4j
public class BankAccountCommandService {

	private final CommandGateway commandGateway;

	public CompletableFuture<BankAccount> createAccount(CreateBankAccountDTO createBankAccountDTO) {
		
		UUID aggregateIdentifier=UUID.randomUUID();
	     System.out.println("Aggregat identifier =" +UUID.randomUUID());
		 log.debug("Aggregat identifier =" +UUID.randomUUID());
		
		
		return this.commandGateway.send(new CreateAccountCommand(aggregateIdentifier,
				createBankAccountDTO.getInitialBalance(), createBankAccountDTO.getName()));
	}
	
	

	public CompletableFuture<String> depositeMoneyToAccount(String accountId, MoneyDTO moneyDepositeDTO) {
	
		return this.commandGateway.send(new TransactionCommand(formatUuid(accountId),moneyDepositeDTO.getAmount(),
				new Timestamp(System.currentTimeMillis()).toInstant(),Constants.DEPOSITE));
				
	}

	public CompletableFuture<String> withdrawMoneyFromAccount(String accountId, MoneyDTO moneyWithdrawDTO) {
		return this.commandGateway.send(new TransactionCommand(formatUuid(accountId),moneyWithdrawDTO.getAmount(),
				new Timestamp(System.currentTimeMillis()).toInstant(),Constants.WITHDRAW));
	}
}
