package com.example.kafkademoappv2.consumer;

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

  private final Logger LOG = LoggerFactory.getLogger(CustomerEventListener.class);

  @KafkaHandler
  public void upsert(CustomerUpsertedEvent event) {
    LOG.info("Received customer upsert: {}", event);

    if ("error".equals(event.getCustomer().getName())) {
      throw new RuntimeException("Invalid customer name");
    }
  }

  @KafkaHandler
  public void delete(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String customerId, @Payload(required = false) KafkaNull tombstone) {
    LOG.info("Received customer delete: {}", customerId);
  }

  @KafkaHandler(isDefault = true)
  public void defaultValue() {
    LOG.info("Received event we are not interested in");
  }
}
