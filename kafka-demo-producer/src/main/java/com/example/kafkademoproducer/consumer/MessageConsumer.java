package com.example.kafkademoproducer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

  @KafkaListener(topics = "topic1")
  public void listen(String message) {
    logger.info("Received: {}", message);
  }
}
