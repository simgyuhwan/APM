package com.terra.cpu.common.influxdb.query;

import org.springframework.util.Assert;

public record TimeRange(String start, String stop) {

  public static TimeRange ofDuration(String duration) {
    Assert.isTrue(TimeRangeType.contains(duration), "This duration is not allowed.");
    return new TimeRange("-" + duration, "now()");
  }

  public String toInfluxDBFormat() {
    return String.format("range(start: %s, stop: %s)", start, stop);
  }
}
