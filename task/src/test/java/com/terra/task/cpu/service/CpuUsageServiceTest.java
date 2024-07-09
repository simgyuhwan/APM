package com.terra.task.cpu.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.terra.task.TaskApplication;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.util.DataSQLGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql("classpath:db/data.sql")
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest(classes = TaskApplication.class)
class CpuUsageServiceTest {

  @Autowired
  CpuUsageService cpuUsageService;

  @Test
  void 지정한_시간_구간의_분_단위_CPU_사용률을_조회할수_있다() {
    // given
    LocalDateTime startDateTime = DataSQLGenerator.START_DATE;
    LocalDateTime endDateTime = startDateTime.plusMinutes(3);

    BigDecimal expectedCpuUsage1 = new BigDecimal("10.0");
    BigDecimal expectedCpuUsage2 = new BigDecimal("30.0");
    BigDecimal expectedCpuUsage3 = new BigDecimal("80.0");

    // when
    List<CpuUsage> cpuUsageList = cpuUsageService.findCpuUsage(startDateTime, endDateTime);

    // then
    assertThat(cpuUsageList).hasSize(3);
    assertThat(cpuUsageList).extracting(CpuUsage::getCpuPercentage)
        .containsExactlyInAnyOrder(
            expectedCpuUsage1,
            expectedCpuUsage2,
            expectedCpuUsage3
        );
  }
}