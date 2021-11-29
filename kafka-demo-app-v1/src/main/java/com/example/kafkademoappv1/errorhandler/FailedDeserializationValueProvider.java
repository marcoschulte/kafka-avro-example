package com.example.kafkademoappv1.errorhandler;

import java.util.function.Function;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;

public class FailedDeserializationValueProvider implements Function<FailedDeserializationInfo, FailedDeserializationInfo> {
  @Override
  public FailedDeserializationInfo apply(FailedDeserializationInfo failedDeserializationInfo) {
    return failedDeserializationInfo;
  }
}
