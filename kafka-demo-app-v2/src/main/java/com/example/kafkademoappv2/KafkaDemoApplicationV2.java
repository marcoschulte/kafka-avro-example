package com.example.kafkademoappv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({AppProperties.class})
public class KafkaDemoApplicationV2 {

  public static void main(String[] args) {
    SpringApplication.run(KafkaDemoApplicationV2.class, args);
  }

}
