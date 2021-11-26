package com.example.kafkademoappv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({AppProperties.class})
public class KafkaDemoApplicationV1 {

  public static void main(String[] args) {
    SpringApplication.run(KafkaDemoApplicationV1.class, args);
  }

}
