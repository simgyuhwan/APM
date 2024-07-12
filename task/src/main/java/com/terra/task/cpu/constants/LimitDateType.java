package com.terra.task.cpu.constants;

import java.time.LocalDateTime;

public enum LimitDateType {
  HOURS(3) {
    @Override
    public LocalDateTime getLimitDateTime() {
      return LocalDateTime.now().minusMonths(limit);
    }
  },
  DAYS(1) {
    @Override
    public LocalDateTime getLimitDateTime() {
      return LocalDateTime.now().minusYears(limit);
    }
  };

  LimitDateType(int limit) {
    this.limit = limit;
  }

  protected final int limit;

  public abstract LocalDateTime getLimitDateTime();
}
