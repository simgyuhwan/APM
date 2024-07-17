package com.terra.cpu.schedule;

import com.terra.cpu.domain.CpuMetric;
import com.terra.cpu.domain.MemMetric;
import com.terra.cpu.service.MetricService;
import com.terra.cpu.service.SystemStatsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricCollector {

  private final MetricService metricService;
  private final SystemStatsInfo systemStatsInfo;

  @Scheduled(fixedDelay = 60000)
  public void collectMetrics() {
    CpuMetric cpuMetric = systemStatsInfo.getCpuMetric();
    MemMetric memMetric = systemStatsInfo.getMemMetric();

    metricService.saveMetric(cpuMetric);
    metricService.saveMetric(memMetric);
  }

}
