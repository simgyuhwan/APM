package com.terra.cpu.common.influxdb.query;

public enum MeasurementType {
  CPU("cpu"),
  MEMORY("memory"),
  DISK("disk");

  MeasurementType(String value) {
    this.value = value;
  }

  private final String value;

  public String toInfluxDBFormat() {
    return value;
  }

  public String lowerCaseName() {
    return name().toLowerCase();
  }
}
