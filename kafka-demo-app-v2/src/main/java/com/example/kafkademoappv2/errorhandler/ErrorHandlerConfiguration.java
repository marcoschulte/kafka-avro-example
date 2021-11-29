package com.example.kafkademoappv2.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.event.ContainerStoppedEvent;
import org.springframework.kafka.listener.CommonContainerStoppingErrorHandler;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class ErrorHandlerConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlerConfiguration.class);

  @Bean
  public CommonErrorHandler errorHandler(KafkaTemplate<?, ?> template) {
    //    return infiniteRetriesErrorHandler();
    return deadLetterErrorHandler(template);
    //    return containerStoppingErrorHandler();
  }

  public CommonErrorHandler infiniteRetriesErrorHandler() {
    return new DefaultErrorHandler(
        new FixedBackOff(1000, FixedBackOff.UNLIMITED_ATTEMPTS)
    );
  }

  public CommonErrorHandler deadLetterErrorHandler(KafkaTemplate<?, ?> template) {
    return new DefaultErrorHandler(
        new DeadLetterPublishingRecoverer(template),
        new FixedBackOff(1000, 3)
    );
  }

  public CommonErrorHandler containerStoppingErrorHandler() {
    return new CommonContainerStoppingErrorHandler();
  }

  @EventListener
  public void containerStopped(ContainerStoppedEvent event) {
    LOG.warn("Container stopped, not receiving any more events");
  }
}
