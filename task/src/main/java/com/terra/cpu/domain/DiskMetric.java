package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.math.BigDecimal;

public record DiskMetric(MetricType type, BigDecimal totalSpace, BigDecimal usedSpace,
                         BigDecimal freeSpace, BigDecimal usedPercent, long timestamp) implements
    Metric {

  public DiskMetric(MetricType type, BigDecimal totalSpace, BigDecimal usedSpace,
      BigDecimal freeSpace, BigDecimal usedPercent, long timestamp) {
    this.type = type;
    this.totalSpace = totalSpace;
    this.usedSpace = usedSpace;
    this.freeSpace = freeSpace;
    this.usedPercent = usedPercent;
    this.timestamp = timestamp;
  }

  public DiskMetric(BigDecimal totalSpace, BigDecimal usedSpace, BigDecimal freeSpace,
      BigDecimal usedPercent) {
    this(MetricType.DISK, totalSpace, usedSpace, freeSpace, usedPercent,
        System.currentTimeMillis());
  }

  @Override
  public Point toPont() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("totalSpace", totalSpace)
        .addField("usedSpace", usedSpace)
        .addField("freeSpace", freeSpace)
        .addField("usedPercent", usedPercent);
  }
}
