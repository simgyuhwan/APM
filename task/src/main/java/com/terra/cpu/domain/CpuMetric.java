package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.terra.cpu.common.influxdb.query.MeasurementType;
import java.math.BigDecimal;

public record CpuMetric(MeasurementType type, BigDecimal cpuUsage, BigDecimal cpuLoad,
                        long timestamp) implements Metric {

  public CpuMetric(BigDecimal cpuUsage, BigDecimal cpuLoad) {
    this(MeasurementType.CPU, cpuUsage, cpuLoad, System.currentTimeMillis());
  }

  public CpuMetric(BigDecimal cpuUsage, BigDecimal cpuLoad, long timestamp) {
    this(MeasurementType.CPU, cpuUsage, cpuLoad, timestamp);
  }

  public Point toPont() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("cpuUsage", cpuUsage)
        .addField("cpuLoad", cpuLoad);
  }
}
