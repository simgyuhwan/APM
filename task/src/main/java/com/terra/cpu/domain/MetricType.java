package com.terra.cpu.domain;

public enum MetricType {
  CPU,
  MEMORY,
  DISK;

  public String lowerCaseName() {
    return name().toLowerCase();
  }
}
