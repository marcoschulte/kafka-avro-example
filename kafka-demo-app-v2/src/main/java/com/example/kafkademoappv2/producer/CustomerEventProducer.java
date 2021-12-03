package com.example.kafkademoappv2.producer;

import com.example.kafkademo.Customer;
import com.example.kafkademo.CustomerUpsertedEvent;
import com.example.kafkademoappv2.AppProperties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventProducer {

  private static final Logger LOG = LoggerFactory.getLogger(CustomerEventProducer.class);
  private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");
  private final KafkaTemplate<Object, Object> template;
  private final AppProperties properties;

  public CustomerEventProducer(KafkaTemplate<Object, Object> template, AppProperties properties) {
    this.template = template;
    this.properties = properties;
  }

  @Scheduled(fixedRate = 5000)
  public void sendUpsert() throws ExecutionException, InterruptedException {
    String key = UUID.randomUUID().toString();

    Customer customer = new Customer(key, "CustomerName V2 " + DF.format(new Date()), "surname");
    CustomerUpsertedEvent value = new CustomerUpsertedEvent(customer);

    LOG.info("Sending customer upsert {}={}", key, value);
    template.send(properties.getTopicName(), key, value).get();
  }


  @Scheduled(fixedRate = 5000)
  public void sendDelete() throws ExecutionException, InterruptedException {
    String key = UUID.randomUUID().toString();

    LOG.info("Sending customer delete {}={}", key, null);
    template.send(properties.getTopicName(), key, null).get();
  }
}
