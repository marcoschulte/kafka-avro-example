package com.example.kafkademoappv2.consumer;

import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topicPattern = ".+\\.DLT")
public class DeadLetterListener {

  private final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);

  @KafkaHandler
  public void listenAllDeadLetterTopics(
      @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      byte[] data) {
    logger.error("Received dead letter message on topic {}, partition {}: {}={}", topic, partition, key, data);
  }

}
