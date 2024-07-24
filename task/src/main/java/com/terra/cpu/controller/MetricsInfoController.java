package com.terra.cpu.controller;

import com.terra.cpu.controller.response.CpuMetricResponse;
import com.terra.cpu.controller.response.MemMetricResponse;
import com.terra.cpu.domain.TimeRange;
import com.terra.cpu.repository.MetricRepository;
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


  @GetMapping("/cpu")
  public List<CpuMetricResponse> getCpuMetric(@RequestParam("timeRange") String timeRange) {
    TimeRange range = TimeRange.of(timeRange);
    return metricRepository.queryCpuMetric(range);
  }

  @GetMapping("/mem")
  public List<MemMetricResponse> getMemMetric(@RequestParam("timeRange") String timeRange) {
    TimeRange range = TimeRange.of(timeRange);
    return metricRepository.queryMemMetric(range);
  }

}
