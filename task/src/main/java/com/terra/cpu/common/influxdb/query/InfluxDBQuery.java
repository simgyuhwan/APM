package com.terra.cpu.common.influxdb.query;

public class InfluxDBQuery {

  private String bucket;
  private TimeRange range;
  private MeasurementType measurement;

  public InfluxDBQuery(String bucket, TimeRange range, MeasurementType measurement) {
    this.bucket = bucket;
    this.range = range;
    this.measurement = measurement;
  }

  public String toFluxQuery() {
    return String.format(
        "from(bucket: \"%s\") |> %s |> filter(fn: (r) => r._measurement == \"%s\")",
        bucket, range.toInfluxDBFormat(), measurement.toInfluxDBFormat()
    );
  }
}
