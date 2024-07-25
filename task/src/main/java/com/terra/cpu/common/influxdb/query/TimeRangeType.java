package com.terra.cpu.common.influxdb.query;

public enum TimeRangeType {
  ONE_HOUR("1h"),
  THREE_HOURS("3h"),
  SIX_HOURS("6h"),
  TWELVE_HOURS("12h"),
  TWENTY_FOUR_HOURS("24h"),
  SEVEN_DAYS("7d"),
  THIRTY_DAYS("30d");

  private final String value;

  TimeRangeType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static TimeRangeType of(String value) {
    for (TimeRangeType timeRangeType : TimeRangeType.values()) {
      if (timeRangeType.getValue().equals(value)) {
        return timeRangeType;
      }
    }
    return null;
  }

  public static boolean contains(String value) {
    for (TimeRangeType timeRangeType : TimeRangeType.values()) {
      if (timeRangeType.getValue().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
