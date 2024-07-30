package com.terra.cpu.controller.response;

import com.terra.cpu.domain.CpuMetric;
import com.terra.cpu.util.DateUtils;
import java.math.BigDecimal;

public record CpuMetricResponse(BigDecimal cpuUsage, BigDecimal cpuLoad, String date) {

  public static CpuMetricResponse of(CpuMetric cpuMetric) {
    String date = DateUtils.timestampToLocalDate(cpuMetric.timestamp());
    return new CpuMetricResponse(cpuMetric.cpuUsage(), cpuMetric.cpuLoad(), date);
  }

}
