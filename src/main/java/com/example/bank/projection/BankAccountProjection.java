package com.example.bank.projection;

import java.sql.Timestamp;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.bank.Exception.AccountNotFoundException;
import com.example.bank.Exception.InsufficientFundException;
import com.example.bank.entity.BankAccount;
import com.example.bank.event.CreateAccountEvent;
import com.example.bank.event.DepositeEvent;
import com.example.bank.event.TransactionEvent;
import com.example.bank.event.WithdrawEvent;
import com.example.bank.model.Constants;
import com.example.bank.query.FindBankAccountQuery;
import com.example.bank.repository.BankAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountProjection {

	
	  private final BankAccountRepository repository;
	   


	    @EventHandler
	    public void on(CreateAccountEvent event) {
	    	
	    	   log.info("Start BankAccountProjection Event Handling Bank Account creation command {}", event.getId());
	      
	       
	        
	        BankAccount bankAccount = new BankAccount(
	                event.getId(),
	                event.getName(),
	                event.getInitialBalance(),
	                new Timestamp(System. currentTimeMillis()).toInstant()
	        );
	        
	        log.info("Save to Repository.....Bank Account...  {}"+ bankAccount.getAccountNumber());
		      
	        this.repository.save(bankAccount);
	    }

	    @EventHandler
	    public void on(DepositeEvent event) throws AccountNotFoundException {
	        log.debug("Handling a Bank Account Credit command {}", event.getId());
	        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
	        if (optionalBankAccount.isPresent()) {
	            BankAccount bankAccount = optionalBankAccount.get();
	            bankAccount.setBalance(bankAccount.getBalance().add(event.getDepositAmount()));
	            bankAccount.setLastupdatedTimestamp(new Timestamp(System. currentTimeMillis()).toInstant());
	            
	            this.repository.save(bankAccount);
	        } else {
	            throw new AccountNotFoundException(event.getId());
	        }
	    }

	    @EventHandler
	    public void on(WithdrawEvent event) throws AccountNotFoundException, InsufficientFundException {
	        log.debug("Handling a Bank Account Debit command {}", event.getId());
	        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
	        if (optionalBankAccount.isPresent()) {
	            BankAccount bankAccount = optionalBankAccount.get();
	            
	            if(bankAccount.getBalance().doubleValue()>event.getWithdrawAmount().doubleValue()) {
	            	  bankAccount.setBalance(bankAccount.getBalance().subtract(event.getWithdrawAmount()));
	            	  bankAccount.setLastupdatedTimestamp(new Timestamp(System. currentTimeMillis()).toInstant());
	  	            this.repository.save(bankAccount);
	            }else {
	            	throw new InsufficientFundException(event.getId(),event.getWithdrawAmount());
	            }
	            
	          
	        } else {
	            throw new AccountNotFoundException(event.getId());
	        }
	    }
	    
	    
	    
	    @EventHandler
	    public void on(TransactionEvent event) throws AccountNotFoundException , InsufficientFundException{
	        log.debug("Handling a Bank Account Transaction event  command {} Trnasaction type  {}", event.getId(), event.getTransactionType());
	       
	        
	        if(event.getTransactionType().equalsIgnoreCase(Constants.WITHDRAW)) {
	        	Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
		        if (optionalBankAccount.isPresent()) {
		            BankAccount bankAccount = optionalBankAccount.get();
		            
		            if(bankAccount.getBalance().doubleValue()>event.getAmount().doubleValue()) {
		            	  bankAccount.setBalance(bankAccount.getBalance().subtract(event.getAmount()));
		            	  bankAccount.setLastupdatedTimestamp(new Timestamp(System. currentTimeMillis()).toInstant());
		  	            this.repository.save(bankAccount);
		            }else {
		            	throw new InsufficientFundException(event.getId(),event.getAmount());
		            }
		            
		          
		        } else {
		            throw new AccountNotFoundException(event.getId());
		        }
	        	
	        }else if(event.getTransactionType().equalsIgnoreCase(Constants.DEPOSITE)) {
	        	 Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
	 	        if (optionalBankAccount.isPresent()) {
	 	            BankAccount bankAccount = optionalBankAccount.get();
	 	            bankAccount.setBalance(bankAccount.getBalance().add(event.getAmount()));
	 	            bankAccount.setLastupdatedTimestamp(new Timestamp(System. currentTimeMillis()).toInstant());
	 	            this.repository.save(bankAccount);
	 	        } else {
	 	            throw new AccountNotFoundException(event.getId());
	 	        }
	        }
	        	
	        
	       
	    }

	    @QueryHandler
	    public BankAccount handle(FindBankAccountQuery query) {
	        log.debug("Handling FindBankAccountQuery query: {}", query);
	        return this.repository.findById(query.getAccountId()).orElse(null);
	    }
	
	
}
