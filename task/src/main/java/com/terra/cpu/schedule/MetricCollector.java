package com.terra.cpu.schedule;

import com.terra.cpu.service.MetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class MetricCollector {

  private final MetricService metricService;

  @Scheduled(fixedDelay = 1000)
  public void collectMetrics() {

  }

}
