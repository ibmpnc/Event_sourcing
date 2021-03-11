package com.example.bank.resource;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entity.BankAccount;
import com.example.bank.service.BankAccountQueryService;
import com.example.bank.validator.CheckDateFormat;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Bank Account Queries")
@AllArgsConstructor
public class BankAccountQueryResource {

	@Autowired	
	BankAccountQueryService bankAccountQueryService;

	    @GetMapping("/{accountId}")
	    public CompletableFuture<BankAccount> findById(@PathVariable("accountId") String accountId) {
	        return this.bankAccountQueryService.findById(accountId);
	    }

	    @GetMapping(value="/{accountId}/events" , params = { "start", "end" } )
	    public List<Object> listEventsForAccount(@PathVariable(value = "accountId") String accountId, 
	    		@RequestParam("start") @CheckDateFormat(pattern = "yyyy-MM-dd")String start, @RequestParam("end") @CheckDateFormat(pattern = "yyyy-MM-dd")String end) throws Exception {
	        //return this.bankAccountQueryService.listEventsForAccount(accountId);
	       		    	
	    			
	    			return this.bankAccountQueryService.listEventsForAccount(accountId, new SimpleDateFormat("yyyy-MM-dd").parse(start).toInstant(), new SimpleDateFormat("yyyy-MM-dd").parse(end).toInstant());
	   	 	
	    	
	    	//return this.bankAccountQueryService.listEventsForAccount(accountId, Instant.parse(start), Instant.parse(end));
	    }

	    
	    
	    @GetMapping(value="/{accountId}/transactions/{transactionType}/events" , params = { "start", "end" } )
	    public List<Object> listEventsForAccount(@PathVariable(value = "accountId") String accountId, @PathVariable(value = "transactionType") String transactionType,
	    		@RequestParam("start") @CheckDateFormat(pattern = "yyyy-MM-dd")String start, @RequestParam("end") @CheckDateFormat(pattern = "yyyy-MM-dd")String end
	    		) throws Exception {
	        //return this.bankAccountQueryService.listEventsForAccount(accountId);
	       		    	
	    			
	    			return this.bankAccountQueryService.listEventsForAccount(accountId, new SimpleDateFormat("yyyy-MM-dd").parse(start).toInstant(), new SimpleDateFormat("yyyy-MM-dd").parse(end).toInstant(),transactionType);
	   	 	
	    	
	    	//return this.bankAccountQueryService.listEventsForAccount(accountId, Instant.parse(start), Instant.parse(end));
	    }
	  
	    
	    
	    @GetMapping("/reachable")
	    public boolean reachable() {
	        return true;
	    }
	    
	   
}
