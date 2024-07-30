package com.terra.cpu.controller;

import com.terra.cpu.domain.CpuEntity;
import com.terra.cpu.domain.MemEntity;
import com.terra.cpu.repository.MetricRepository;
import com.terra.cpu.service.MetricsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/metrics")
@RestController
@RequiredArgsConstructor
public class MetricsInfoController {

  private final MetricRepository metricRepository;
  private final MetricsService metricsService;

  @GetMapping("/cpu")
  public List<CpuEntity> getCpuMetric(@RequestParam("timeRange") String timeRange) {
    return metricsService.getCpuMetric(timeRange);
  }

  @GetMapping("/mem")
  public List<MemEntity> getMemMetric(@RequestParam("timeRange") String timeRange) {
    return metricsService.getMemMetric(timeRange);
  }

}
