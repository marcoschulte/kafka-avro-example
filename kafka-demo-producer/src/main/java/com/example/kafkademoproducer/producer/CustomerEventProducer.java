package com.example.kafkademoproducer.producer;

import com.example.kafkademo.Customer;
import com.example.kafkademo.CustomerUpsertedEvent;
import com.example.kafkademoproducer.AppProperties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventProducer {

  private static final Logger log = LoggerFactory.getLogger(CustomerEventProducer.class);
  private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");
  private final KafkaTemplate<Object, Object> template;
  private final AppProperties properties;

  public CustomerEventProducer(KafkaTemplate<Object, Object> template, AppProperties properties) {
    this.template = template;
    this.properties = properties;
  }

  @Scheduled(fixedRate = 5000)
  public void sendUpsert() {
    String key = UUID.randomUUID().toString();

    Customer customer = new Customer(key, "CustomerName " + DF.format(new Date()));
    CustomerUpsertedEvent value = new CustomerUpsertedEvent(customer);

    log.info("Sending customer upsert {}={}", key, value);
    template.send(properties.getTopicName(), key, value);
  }


  @Scheduled(fixedRate = 5000)
  public void sendDelete() {
    String key = UUID.randomUUID().toString();

    log.info("Sending customer delete {}={}", key, null);
    template.send(properties.getTopicName(), key, null);
  }
}
