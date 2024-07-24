package com.terra.cpu.domain;

public enum TimeRange {
  ONE_HOUR("1h"),
  THREE_HOURS("3h"),
  SIX_HOURS("6h"),
  TWELVE_HOURS("12h"),
  TWENTY_FOUR_HOURS("24h"),
  SEVEN_DAYS("7d"),
  THIRTY_DAYS("30d");

  private final String value;

  TimeRange(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static TimeRange of(String value) {
    for (TimeRange timeRange : TimeRange.values()) {
      if (timeRange.getValue().equals(value)) {
        return timeRange;
      }
    }
    return null;
  }
}
