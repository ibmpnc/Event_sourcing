package com.example.bank.aggregate;

import java.math.BigDecimal;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bank.Exception.InsufficientFundException;
import com.example.bank.command.CreateAccountCommand;
import com.example.bank.command.DepositeCommand;
import com.example.bank.command.TransactionCommand;
import com.example.bank.command.WithdrawCommand;
import com.example.bank.event.CreateAccountEvent;
import com.example.bank.event.DepositeEvent;
import com.example.bank.event.TransactionEvent;
import com.example.bank.event.WithdrawEvent;
import com.example.bank.service.KafkaEngine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
@Slf4j
public class BankAccountAggregate {

	
	
	   @AggregateIdentifier
	    private UUID id;
	    private BigDecimal balance;
	    private String name;
	    
  
 

	    @CommandHandler
	    public BankAccountAggregate(CreateAccountCommand command) {
	    	  log.info("start BankAccountAggregate  accountid: {}", command.getAccountId());
          
	        AggregateLifecycle.apply(
	                new CreateAccountEvent(
	                        command.getAccountId(),
	                        command.getInitialBalance(),
	                        command.getName()
	                )
	        );
	        
	        
	        
	        log.info("End BankAccountAggregate  accountid: {}", command.getAccountId());
	    }

	    @EventSourcingHandler
	    public void on(CreateAccountEvent event) {
	    	
	    	log.info("Start EventSourcingHandler for CreateAccountEvent  accountid: {}", event.getId());
	        this.id = event.getId();
	        this.name = event.getName();
	        this.balance = event.getInitialBalance();
	        log.info("End EventSourcingHandler for CreateAccountEvent  accountid: {}", event.getId());
	    }

	    @CommandHandler
	    public void handle(TransactionCommand command) {
	    	
	    	  	AggregateLifecycle.apply(
	                new TransactionEvent(
	                        command.getAccountId(),
	                        command.getAmount(),
	                        command.getTransactionTs(),
	                        command.getTransactionType()
	                )
	        );
	    }
	    
	    
	    @EventSourcingHandler
	    public void on(TransactionEvent event) {
	        this.balance = this.balance.add(event.getAmount());
	    }

	    @EventSourcingHandler
	    public void on(DepositeEvent event) {
	        this.balance = this.balance.add(event.getDepositAmount());
	    }

	    @CommandHandler
	    public void handle(WithdrawCommand command) {
	        AggregateLifecycle.apply(
	                new WithdrawEvent(
	                        command.getAccountId(),
	                        command.getWithdrawAmount()
	                )
	        );
	    }

	    @EventSourcingHandler
	    public void on(WithdrawEvent event) throws InsufficientFundException {
	        if (this.balance.compareTo(event.getWithdrawAmount()) < 0) {
	            throw new InsufficientFundException(event.getId(), event.getWithdrawAmount());
	        }
	        this.balance = this.balance.subtract(event.getWithdrawAmount());
	    }
	
	
}
