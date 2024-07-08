package com.terra.task.cpu.util;

import java.util.Random;

public class RandomValueGenerator {

  public static double generateRandomValue(Random random) {
    double value = 100 * random.nextDouble();
    return (Math.round(10 * value) / 10.0);
  }
}
