package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.math.BigDecimal;

public record MemMetric(MetricType type, BigDecimal totalMem, BigDecimal usedMem,
                        BigDecimal freeMem, BigDecimal usedPercent, long timestamp) implements
    Metric {

  public MemMetric(MetricType type, BigDecimal totalMem, BigDecimal usedMem, BigDecimal freeMem,
      BigDecimal usedPercent, long timestamp) {
    this.type = type;
    this.totalMem = totalMem;
    this.usedMem = usedMem;
    this.freeMem = freeMem;
    this.usedPercent = usedPercent;
    this.timestamp = timestamp;
  }

  public MemMetric(BigDecimal totalMem, BigDecimal usedMem, BigDecimal freeMem,
      BigDecimal usedPercent) {
    this(MetricType.MEMORY, totalMem, usedMem, freeMem, usedPercent, System.currentTimeMillis());
  }

  @Override
  public Point toPont() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("totalMem", totalMem)
        .addField("usedMem", usedMem)
        .addField("freeMem", freeMem)
        .addField("usedPercent", usedPercent);
  }
}