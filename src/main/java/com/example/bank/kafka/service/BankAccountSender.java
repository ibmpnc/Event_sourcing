package com.example.bank.kafka.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


import com.example.common.model.KafkaBankAccount;

@Service
public class BankAccountSender {

	private static final Logger LOG = LoggerFactory.getLogger(BankAccountSender.class);

    @Autowired
    private KafkaTemplate<String, KafkaBankAccount> kafkaTemplate;

    @Value("${app.topic.example}")
    private String topic;

    public void send(KafkaBankAccount data){
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        Message<KafkaBankAccount> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
    }
}
