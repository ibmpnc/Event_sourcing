package com.example.bank.resource;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.entity.BankAccount;
import com.example.bank.kafka.service.BankAccountSender;
import com.example.bank.model.CreateBankAccountDTO;
import com.example.bank.model.MoneyDTO;
import com.example.bank.service.BankAccountCommandService;
import com.example.common.model.KafkaBankAccount;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Api(value = "Bank  Commands")
@AllArgsConstructor
@Slf4j
public class BankAccountCommandResource {

	 @Autowired	
	 BankAccountCommandService bankAccountCommandService;
	
	 
	 //private final KafkaEngine kafkaEngine;
	
	 
	 private final BankAccountSender bankAccountSender;
	

	    @Autowired
	   BankAccountCommandResource(BankAccountSender bankAccountSender) {
	        this.bankAccountSender = bankAccountSender;
	    }
	 
	 
	 
	 
	@RequestMapping(method = RequestMethod.POST, value = "/accounts")
    @ResponseStatus(value = CREATED)
    public CompletableFuture<BankAccount> createAccount(@RequestBody CreateBankAccountDTO createBankAccountDTO) {
        
		//kafkaEngine.sendMessage("Sending Msg for Create Account.....");
		
		
		KafkaBankAccount bnkaccnt=new KafkaBankAccount(""+createBankAccountDTO.getInitialBalance(), createBankAccountDTO.getName(), createBankAccountDTO.getAddress(), createBankAccountDTO.getAccountType());
		
		bankAccountSender.send(bnkaccnt);
		
		
		//return this.bankAccountCommandService.createAccount(createBankAccountDTO);
		return null;
		
    }
	
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/kafka_accounts")
    @ResponseStatus(value = CREATED)
    public void kafkaBankAccountPost(@RequestBody KafkaBankAccount bankAccount) throws Exception{
        
		
		
		log.info("Kafka Account posted ...   "+ bankAccount);
		
		CreateBankAccountDTO createBankAccountDTO= new CreateBankAccountDTO();
		createBankAccountDTO.setAccountType(bankAccount.getAccountType());
		createBankAccountDTO.setAddress(bankAccount.getAddress());
		createBankAccountDTO.setInitialBalance(new BigDecimal(bankAccount.getBalance()));
		createBankAccountDTO.setName(bankAccount.getName());
		
		this.bankAccountCommandService.createAccount(createBankAccountDTO);
	
	
    }
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/kafka_accounts_backup")
    @ResponseStatus(value = CREATED)
    public CompletableFuture<KafkaBankAccount> kafkaBankAccountPost_bakupu(@RequestBody KafkaBankAccount bankAccount) {
        
		//kafkaEngine.sendMessage("Sending Msg for Create Account.....");
		log.debug("Kafka Account posted ...   "+ bankAccount);
		log.info("Kafka Account posted ...   "+ bankAccount);
		
		return null;
    }

   
   
    @RequestMapping(method = RequestMethod.PUT, value = "/withdraw/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyDTO moneyWithdrawDTO) {
        return this.bankAccountCommandService.withdrawMoneyFromAccount(accountId, moneyWithdrawDTO);
    }
    
    @PutMapping(value = "/deposite/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyDTO moneyDepositeDTO) {
        return this.bankAccountCommandService.depositeMoneyToAccount(accountId, moneyDepositeDTO);
    }
    
    @GetMapping("/reachable")
    public boolean reachable() {
        return true;
    }
}
