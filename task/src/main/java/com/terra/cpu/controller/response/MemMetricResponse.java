package com.terra.cpu.controller.response;

import com.terra.cpu.domain.MemMetric;
import com.terra.cpu.util.DateUtils;
import java.math.BigDecimal;

public record MemMetricResponse(BigDecimal totalMem, BigDecimal usedMem, BigDecimal freeMem,
                                BigDecimal usedPercent, String date) {

  public static MemMetricResponse of(MemMetric memMetric) {
    String date = DateUtils.timestampToLocalDate(memMetric.timestamp());
    return new MemMetricResponse(memMetric.totalMem(), memMetric.usedMem(), memMetric.freeMem(),
        memMetric.usedPercent(), date);
  }
}
