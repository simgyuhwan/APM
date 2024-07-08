package com.terra.task.cpu.util;

import java.math.BigDecimal;
import java.util.Random;

public class RandomValueGenerator {

  public static BigDecimal generateRandomValue(Random random) {
    double value = 100 * random.nextDouble();
    double cpuUsage = Math.round(10 * value) / 10.0;
    return new BigDecimal(cpuUsage);
  }
}
