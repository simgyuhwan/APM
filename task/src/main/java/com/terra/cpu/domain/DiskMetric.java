package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.terra.cpu.common.influxdb.query.MeasurementType;
import java.math.BigDecimal;

public record DiskMetric(MeasurementType type, BigDecimal totalSpace, BigDecimal usedSpace,
                         BigDecimal freeSpace, BigDecimal usedPercent, long timestamp) implements
    Metric {

  public DiskMetric(BigDecimal totalSpace, BigDecimal usedSpace, BigDecimal freeSpace,
      BigDecimal usedPercent) {
    this(MeasurementType.DISK, totalSpace, usedSpace, freeSpace, usedPercent,
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
