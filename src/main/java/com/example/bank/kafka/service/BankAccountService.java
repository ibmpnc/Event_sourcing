package com.example.bank.kafka.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bank.command.CreateAccountCommand;

import com.example.bank.model.CreateBankAccountDTO;
import com.example.bank.service.BankAccountCommandService;
import com.example.common.model.KafkaBankAccount;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class BankAccountService {

	
	@Autowired
	RestTemplate restTemplate	;
	
	
	
	@Value("${END_POINT_URL}")
    private String END_POINT_URL;
	
public void createAccount(KafkaBankAccount bankAccount) {
		
		
	

	HttpEntity<KafkaBankAccount> request = new HttpEntity<>(bankAccount);
	restTemplate.postForObject(END_POINT_URL, request, KafkaBankAccount.class);
	
	
	
	
	
	}
}
