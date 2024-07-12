package com.terra.task.cpu.controller;

import com.terra.task.cpu.constants.LimitDateType;
import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.service.CpuUsageRsp;
import com.terra.task.cpu.service.CpuUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
      @RequestParam("startDateTime") LocalDateTime startDateTime,
      @RequestParam("endDateTime") LocalDateTime endDateTime, @RequestParam("dateType")
  LimitDateType dateType) {
    return cpuUsageService.findCpuUsage(startDateTime, endDateTime, dateType);
  }

}
