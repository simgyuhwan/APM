package com.apm.client.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.math.BigDecimal;

public record MemMetric(MeasurementType type, BigDecimal totalMem, BigDecimal usedMem,
                        BigDecimal freeMem, BigDecimal usedPercent, long timestamp) implements
    Metric {

  public MemMetric(BigDecimal totalMem, BigDecimal usedMem, BigDecimal freeMem,
      BigDecimal usedPercent) {
    this(MeasurementType.MEMORY, totalMem, usedMem, freeMem, usedPercent,
        System.currentTimeMillis());
  }

  @Override
  public Point toPoint() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("totalMem", totalMem)
        .addField("usedMem", usedMem)
        .addField("freeMem", freeMem)
        .addField("usedPercent", usedPercent);
  }
}