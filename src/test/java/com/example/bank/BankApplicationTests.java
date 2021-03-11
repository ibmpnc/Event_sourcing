package com.example.bank;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bank.aggregate.BankAccountAggregate;
import com.example.bank.command.CreateAccountCommand;
import com.example.bank.command.TransactionCommand;
import com.example.bank.event.CreateAccountEvent;
import com.example.bank.event.TransactionEvent;
import com.example.bank.model.Constants;



@SpringBootTest
class BankApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	 private static final String customerName = "David";

	    private FixtureConfiguration<BankAccountAggregate> fixture;
	    private UUID id;

	    @BeforeEach
	    public void setUp() {
	        fixture = new AggregateTestFixture<>(BankAccountAggregate.class);
	        id = UUID.randomUUID();
	    }

	    @Test
	    public void dispatch_accountcreated_event() {
	        fixture.givenNoPriorActivity()
	                .when(new CreateAccountCommand(
	                        id,
	                        BigDecimal.valueOf(1000),
	                        customerName)
	                )
	                .expectEvents(new CreateAccountEvent(
	                        id,
	                        BigDecimal.valueOf(1000),
	                        customerName)
	                );
	    }
	    
	    
	    
	   
	    
	    

	    @Test
	    public void dispatch_transaction_deposite_event() {
	        fixture.given(new CreateAccountEvent(
	                        id,
	                        BigDecimal.valueOf(1000),
	                        customerName))
	                .when(new TransactionCommand(
	                        id,
	                        BigDecimal.valueOf(100),
	                        new Timestamp(System.currentTimeMillis()).toInstant(),
	                        Constants.DEPOSITE)
	                )
	                .expectEvents(new TransactionEvent(
	                        id,
	                        BigDecimal.valueOf(100),
	                        new Timestamp(System.currentTimeMillis()).toInstant(),
	                        Constants.DEPOSITE)
	                );
	    }

	    @Test
	    public void dispatch_transaction_withdraw_event() {
	    	 fixture.given(new CreateAccountEvent(
                     id,
                     BigDecimal.valueOf(1000),
                     customerName))
             .when(new TransactionCommand(
                     id,
                     BigDecimal.valueOf(100),
                     new Timestamp(System.currentTimeMillis()).toInstant(),
                     Constants.WITHDRAW)
             )
             .expectEvents(new TransactionEvent(
                     id,
                     BigDecimal.valueOf(100),
                     new Timestamp(System.currentTimeMillis()).toInstant(),
                     Constants.WITHDRAW)
             );
	    }

	    @Test
	    public void dispatch_transaction_withdraw_event_when_balance_is_lower_than_withdraw_amount() {
	        fixture.given(new CreateAccountEvent(
	                        id,
	                        BigDecimal.valueOf(1000),
	                        customerName))
	                .when(new TransactionCommand(
	                        id,
	                        BigDecimal.valueOf(100),
	                        new Timestamp(System.currentTimeMillis()).toInstant(),
	                        Constants.WITHDRAW))
	                .expectNoEvents();
	        
	        
	       
	        
	        
	    }
	
	
	
	
	
	
	

}
