package com.example.bank.kafka.service;



import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bank.model.CreateBankAccountDTO;
import com.example.common.model.KafkaBankAccount;


@Service
public class BankAccountListener {

	 private static final Logger LOG = LoggerFactory.getLogger(BankAccountListener.class);
	 
	 
	 @Autowired
	 BankAccountService bankAccountService;

	    @KafkaListener(topics = "${app.topic.example}")
	    public void receive(@Payload KafkaBankAccount data,
	                        @Headers MessageHeaders headers) {
	        
	   	bankAccountService.createAccount(data);
	    LOG.info("received data='{}'", data);
        headers.keySet().forEach(key -> {
	            LOG.info("{}: {}", key, headers.get(key));
	        });
	    }

	}

