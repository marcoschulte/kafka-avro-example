package com.example.kafkademoproducer.consumer;

import com.example.kafkademo.CustomerUpsertedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${application.topic-name}")
public class CustomerEventListener {

  private final Logger logger = LoggerFactory.getLogger(CustomerEventListener.class);

  @KafkaHandler
  public void upsert(CustomerUpsertedEvent event) {
    logger.info("Received customer upsert: {}", event);
  }

  @KafkaHandler
  public void delete(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String customerId, @Payload(required = false) KafkaNull tombstone) {
    logger.info("Received customer delete: {}", customerId);
  }
}
