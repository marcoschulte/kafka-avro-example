package com.example.kafkademoappv2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProperties {

  private String topicName;

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }
}
