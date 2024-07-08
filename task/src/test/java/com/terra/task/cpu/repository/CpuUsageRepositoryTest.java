package com.terra.task.cpu.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.terra.task.cpu.config.CommonConfig;
import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.util.DataSQLGenerator;
import com.terra.task.cpu.util.RandomValueGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql("classpath:db/data.sql")
@Testcontainers
@Import(CommonConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class CpuUsageRepositoryTest {

  @Container
  static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.32")
      .withDatabaseName("terra");

  @Autowired
  CpuUsageRepository cpuUsageRepository;

  @Autowired
  Random random;

  @Test
  void 지정한_구간내의_일_단위_CPU_사용률의_최소_최대_평균값을_조회할수있다() {
    // given

    // when

    // then
  }

  @Test
  void 지정한_구간내의_시_단위_CPU_사용률의_최소_최대_평균을_조회할수있다() {
    // given
    LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 10, 1);
    CpuUsage cpuUsage1 = CpuUsage.of(startDate.plusMinutes(1), new BigDecimal(10));
    CpuUsage cpuUsage2 = CpuUsage.of(startDate.plusMinutes(1), new BigDecimal(30));
    CpuUsage cpuUsage3 = CpuUsage.of(startDate.plusMinutes(1), new BigDecimal(50));
    cpuUsageRepository.saveAll(List.of(cpuUsage1, cpuUsage2, cpuUsage3));

    // when
    List<CpuStats> cpuStatsList = cpuUsageRepository.findHourlyCpuUsageStatsByDate(startDate,
        startDate.plusHours(1));

    // then
    CpuStats cpuStats = cpuStatsList.get(0);

    assertThat(cpuStats.maxUsage()).isEqualTo(new BigDecimal("50.0"));
    assertThat(cpuStats.minUsage()).isEqualTo(new BigDecimal("10.0"));
    assertThat(cpuStats.avgUsage()).isEqualTo(new BigDecimal("30.00"));
  }

  @Test
  void 지정한_구간내의_분_단위_CPU_사용률을_조회할_수_있다() {
    // given
    LocalDateTime startDate = DataSQLGenerator.START_DATE;
    LocalDateTime endDate = startDate.plusMinutes(60);

    // when
    List<CpuUsage> cpuUsageList = cpuUsageRepository.findMinuteCpuUsage(startDate, endDate);

    // then
    assertThat(cpuUsageList).hasSize(61);
  }

  /**
   * 지정한 시간 범위 동안의 CPU 사용량 데이터를 랜덤 값으로 생성하여 저장합니다.
   *
   * @param startDateTime 시작 시간
   * @param endDateTime   종료 시간
   */
  void insertCpuUsageDataForMinuteRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    long minuteBetween = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    List<CpuUsage> cpuUsageList = new ArrayList<>();

    for (int minute = 0; minute < minuteBetween; minute++) {
      LocalDateTime nextDay = startDateTime.plusMinutes(minute);
      BigDecimal cpuUsage = RandomValueGenerator.generateRandomValue(random);
      cpuUsageList.add(CpuUsage.of(nextDay, cpuUsage));
    }
    cpuUsageRepository.saveAll(cpuUsageList);
  }

}

