package com.terra.cpu.controller;

import com.terra.cpu.constants.LimitDateType;
import com.terra.cpu.controller.response.CpuMetricResponse;
import com.terra.cpu.controller.response.MemMetricResponse;
import com.terra.cpu.domain.CpuStats;
import com.terra.cpu.repository.MetricRepository;
import com.terra.cpu.service.CpuUsageRsp;
import com.terra.cpu.service.CpuUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cpu Usage")
@RequestMapping("/cpu")
@RestController
@RequiredArgsConstructor
public class CpuUsageController {

  private final CpuUsageService cpuUsageService;
  private final MetricRepository metricRepository;

  @Operation(summary = "분단위 CPU 사용량 조회", description = "분단위 CPU 사용량 조회 기능")
  @GetMapping("/usage/minutes")
  public List<CpuUsageRsp> getMinutesCpuUsage(
      @RequestParam("startDateTime") LocalDateTime startDateTime,
      @RequestParam("endDateTime") LocalDateTime endDateTime) {
    return cpuUsageService.findMinutesCpuUsage(startDateTime, endDateTime);
  }

  @Operation(summary = "시단위 CPU 사용량 조회", description = "시단위 CPU 사용량 조회 기능")
  @GetMapping("/usage")
  public List<CpuStats> getCpuUsage(
      @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
      @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
      @RequestParam("dateType")
      LimitDateType dateType) {
    return cpuUsageService.findCpuUsage(startDateTime, endDateTime, dateType);
  }

  @GetMapping("/test/cpu")
  public List<CpuMetricResponse> getTestCpuMetric() {
    return metricRepository.queryCpuMetric("2024-07-18T05:50:00Z", "2024-07-18T06:55:00Z");
  }

  @GetMapping("/test/mem")
  public List<MemMetricResponse> getTestMemMetric() {
    return metricRepository.queryMemMetric("2024-07-18T05:50:00Z", "2024-07-18T06:55:00Z");
  }
}
