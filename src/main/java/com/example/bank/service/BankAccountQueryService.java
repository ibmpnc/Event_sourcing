package com.example.bank.service;


import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import com.example.bank.entity.BankAccount;
import com.example.bank.event.TransactionEvent;
import com.example.bank.model.TransactionDTO;
import com.example.bank.model.TransactonType;
import com.example.bank.query.FindBankAccountQuery;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.example.bank.service.ServiceUtil.formatUuid;



@Service
@AllArgsConstructor
public class BankAccountQueryService {
	
	 private final QueryGateway queryGateway;
	    private final EventStore eventStore;

	    public CompletableFuture<BankAccount> findById(String accountId) {
	        return this.queryGateway.query(
	                new FindBankAccountQuery(formatUuid(accountId)),
	                ResponseTypes.instanceOf(BankAccount.class)
	        );
	    }

	    public List<Object> listEventsForAccount(String accountId) {
	       
	    	    	return this.eventStore
	                .readEvents(formatUuid(accountId).toString())
	                .asStream()
	                .map(Message::getPayload)	                
	                .collect(Collectors.toList());
	    }
	    
	    
 public List<Object> listEventsForAccount(String accountId,Instant start, Instant end) {
	       
	 
		
	 List<Object> events= this.eventStore
	                .readEvents(formatUuid(accountId).toString())
	                .asStream()
	                .filter(e-> 
	                e.getPayload()   instanceof TransactionEvent &&
	               // e.getPayload().getClass().isInstance(TransactionEvent.class)&&
	                 e.getTimestamp().isAfter(start) &&  
	                 e.getTimestamp().isBefore(end) 
	                               
	                 )
	                 .map(Message::getPayload)	  
	               // .map(e -> new TransactionDTO(e.getAggregateIdentifier(), e.getPayloadType(). e.getType().equalsIgnoreCase("") ))
	                .collect(Collectors.toList());
	 
	
	 
	 return events;
	    }
 
 
 public List<Object> listEventsForAccount(String accountId,Instant start, Instant end ,String transactionType) {
     
	 
		
	 List<Object> events= this.eventStore
	                .readEvents(formatUuid(accountId).toString())
	                .asStream()
	                .filter(e-> 
	                e.getPayload()   instanceof TransactionEvent &&
	               // e.getPayload().getClass().isInstance(TransactionEvent.class)&&
	                 e.getTimestamp().isAfter(start) &&  
	                 e.getTimestamp().isBefore(end) && 
	                 ((TransactionEvent) e.getPayload()).getTransactionType().equalsIgnoreCase(transactionType)               
	                 )
	                 .map(Message::getPayload)	  
	               // .map(e -> new TransactionDTO(e.getAggregateIdentifier(), e.getPayloadType(). e.getType().equalsIgnoreCase("") ))
	                .collect(Collectors.toList());
	 
	
	 
	 return events;
	    }


}
