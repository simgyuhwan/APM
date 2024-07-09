package com.terra.task.cpu.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.util.DataSQLGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql("classpath:db/data.sql")
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class CpuUsageRepositoryTest {

  @Container
  static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.32")
      .withDatabaseName("terra");

  @Autowired
  CpuUsageRepository cpuUsageRepository;

  @Test
  void 지정한_구간내의_일_단위_CPU_사용률의_최소_최대_평균값을_조회할수있다() {
    // given
    LocalDateTime startDate = DataSQLGenerator.START_DATE;
    LocalDateTime endDate = startDate.plusDays(3);

    BigDecimal expectedMin = new BigDecimal("10.0");
    BigDecimal expectedMax = new BigDecimal("80.0");
    BigDecimal expectedAvg = new BigDecimal("40.00");

    // when
    List<CpuStats> cpuStatsList = cpuUsageRepository.findDailyCpuUsageStatsByDate(startDate,
        endDate);

    // then
    assertThat(cpuStatsList).isNotEmpty();
    assertThat(cpuStatsList).hasSize(3);

    CpuStats cpuStats = cpuStatsList.get(0);
    assertThat(cpuStats.maxUsage()).isEqualTo(expectedMax);
    assertThat(cpuStats.minUsage()).isEqualTo(expectedMin);
    assertThat(cpuStats.avgUsage()).isEqualTo(expectedAvg);
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
    LocalDateTime endDate = startDate.plusMinutes(3);

    // when
    List<CpuUsage> cpuUsageList = cpuUsageRepository.findMinuteCpuUsageByDate(startDate, endDate);

    // then
    assertThat(cpuUsageList).hasSize(3);
  }

}

