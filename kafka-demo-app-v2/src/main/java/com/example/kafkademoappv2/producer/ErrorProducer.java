package com.example.kafkademoappv2.producer;

import com.example.kafkademo.Customer;
import com.example.kafkademo.CustomerUpsertedEvent;
import com.example.kafkademoappv2.AppProperties;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorProducer {

  private final AppProperties properties;
  private final KafkaProperties kafkaProperties;
  private final KafkaTemplate template;

  public ErrorProducer(AppProperties properties, KafkaProperties kafkaProperties,
      KafkaTemplate template) {
    this.properties = properties;
    this.kafkaProperties = kafkaProperties;
    this.template = template;
  }

  @GetMapping("/poison-pill")
  public void sendPoisonPill() throws ExecutionException, InterruptedException {
    Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
    producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
    Producer<Object, Object> producer = new DefaultKafkaProducerFactory<>(producerProperties).createProducer();

    String key = UUID.randomUUID().toString();
    Bytes value = Bytes.wrap(new byte[] {0, 1});
    ProducerRecord<Object, Object> record = new ProducerRecord<>(properties.getTopicName(), key, value);

    producer.send(record).get();
    producer.close();
  }

  @GetMapping("/business-exception")
  public void causeBusinessException() throws ExecutionException, InterruptedException {
    String key = UUID.randomUUID().toString();
    template.send(properties.getTopicName(), key,
        CustomerUpsertedEvent.newBuilder()
            .setCustomer(Customer.newBuilder()
                .setId(key)
                .setName("error")
                .setSurname("Surname")
                .build())
            .build()).get();
  }


}
