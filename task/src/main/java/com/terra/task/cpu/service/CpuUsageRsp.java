package com.terra.task.cpu.service;

import com.terra.task.cpu.domain.CpuUsage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CpuUsageRsp(LocalDateTime date, BigDecimal cpuPercentage) {

  public static CpuUsageRsp from(CpuUsage cpuUsage) {
    return new CpuUsageRsp(cpuUsage.getTimestamp(), cpuUsage.getCpuPercentage());
  }
}
