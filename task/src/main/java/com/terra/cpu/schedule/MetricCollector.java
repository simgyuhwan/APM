package com.terra.cpu.schedule;

import com.terra.cpu.controller.response.CpuMetricResponse;
import com.terra.cpu.controller.response.MemMetricResponse;
import com.terra.cpu.controller.response.RealTimeMetrics;
import com.terra.cpu.domain.CpuMetric;
import com.terra.cpu.domain.MemMetric;
import com.terra.cpu.repository.MetricRepository;
import com.terra.cpu.service.SystemStatsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricCollector {

  private final MetricRepository metricRepository;
  private final SystemStatsInfo systemStatsInfo;
  private final SimpMessagingTemplate messagingTemplate;

  @Scheduled(fixedDelay = 60000)
  public void collectMetrics() {
    CpuMetric cpuMetric = systemStatsInfo.getCpuMetric();
    MemMetric memMetric = systemStatsInfo.getMemMetric();

    saveMetrics(cpuMetric, memMetric);
//    sendRealTimeMetrics(cpuMetric, memMetric);
  }

  @Scheduled(fixedDelay = 1000)
  public void sendMetrics() {
    CpuMetric cpuMetric = systemStatsInfo.getCpuMetric();
    MemMetric memMetric = systemStatsInfo.getMemMetric();
    sendRealTimeMetrics(cpuMetric, memMetric);
  }

  private void sendRealTimeMetrics(CpuMetric cpuMetric, MemMetric memMetric) {
    CpuMetricResponse cpuMetricResponse = CpuMetricResponse.of(cpuMetric);
    MemMetricResponse memMetricResponse = MemMetricResponse.of(memMetric);
    RealTimeMetrics realTimeMetrics = new RealTimeMetrics(cpuMetricResponse, memMetricResponse);

    log.debug("Sending metrics: {}", realTimeMetrics);
    messagingTemplate.convertAndSend("/topic/metrics", realTimeMetrics);
  }

  private void saveMetrics(CpuMetric cpuMetric, MemMetric memMetric) {
    metricRepository.saveMetric(cpuMetric);
    metricRepository.saveMetric(memMetric);
  }

}
