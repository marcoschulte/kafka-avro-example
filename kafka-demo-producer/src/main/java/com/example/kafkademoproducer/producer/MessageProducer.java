package com.example.kafkademoproducer.producer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

  private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
  private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");
  private final KafkaTemplate<Object, Object> template;

  public MessageProducer(KafkaTemplate<Object, Object> template) {
    this.template = template;
  }

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    String key = UUID.randomUUID().toString();
    String value = String.format("It is %s", DF.format(new Date()));

    log.info("Sending message {}={}", key, value);
    template.send("topic1", key, value);
  }
}
