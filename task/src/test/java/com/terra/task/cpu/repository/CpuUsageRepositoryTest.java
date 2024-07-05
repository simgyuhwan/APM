package com.terra.task.cpu.repository;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CpuUsageRepositoryTest {

  @Autowired
  CpuUsageRepository cpuUsageRepository;

  void insertCpuusageDataForTimeRange(LocalDateTime start, LocalDateTime end) {
  }

  public static void main(String[] args) {
    Random random = new Random();
    double value = 100 * random.nextDouble();
    double round = (Math.round(10 * value) / 10.0);
    System.out.println(round);
  }
}

class RandomValueGenerator {

  public static double generateRandomValue(Random random) {
    double value = 100 * random.nextDouble();
    return (Math.round(10 * value) / 10.0);
  }
}