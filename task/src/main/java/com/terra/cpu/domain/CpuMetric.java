package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.math.BigDecimal;

public record CpuMetric(MetricType type, BigDecimal cpuUsage, BigDecimal cpuLoad,
                        long timestamp) implements Metric {

  public CpuMetric(MetricType type, BigDecimal cpuUsage, BigDecimal cpuLoad, long timestamp) {
    this.type = type;
    this.cpuUsage = cpuUsage;
    this.cpuLoad = cpuLoad;
    this.timestamp = timestamp;
  }

  public CpuMetric(BigDecimal cpuUsage, BigDecimal cpuLoad) {
    this(MetricType.CPU, cpuUsage, cpuLoad, System.currentTimeMillis());
  }

  public Point toPont() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("cpuUsage", cpuUsage)
        .addField("cpuLoad", cpuLoad);
  }
}
