package com.terra.cpu.controller.response;

import com.terra.cpu.repository.MetricRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/metrics")
@RestController
@RequiredArgsConstructor
public class MetricsController {

  private final MetricRepository metricRepository;


  @GetMapping("/cpu")
  public List<CpuMetricResponse> getTestCpuMetric(@RequestParam("start") String start,
      @RequestParam("end") String end) {
    return metricRepository.queryCpuMetric(start, end);
  }

  @GetMapping("/mem")
  public List<MemMetricResponse> getTestMemMetric(@RequestParam("start") String start,
      @RequestParam("end") String end) {
    return metricRepository.queryMemMetric(start, end);
  }
}
