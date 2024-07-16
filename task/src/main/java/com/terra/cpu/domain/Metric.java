package com.terra.cpu.domain;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.math.BigDecimal;

public class Metric {

  private MetricType type;
  private BigDecimal value;
  private long timestamp;

  public Metric(MetricType type, BigDecimal value) {
    this.type = type;
    this.value = value;
    this.timestamp = System.currentTimeMillis();
  }

  public Point toPont() {
    return Point.measurement(type.name().toLowerCase())
        .time(timestamp, WritePrecision.MS)
        .addField("value", value);
  }

  public enum MetricType {
    CPU,
    MEMORY,
    DISK
  }
}
