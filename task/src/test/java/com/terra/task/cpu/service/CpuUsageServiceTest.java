package com.terra.task.cpu.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.terra.task.TaskApplication;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.util.DataSQLGenerator;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = TaskApplication.class)
class CpuUsageServiceTest {

  @Autowired
  CpuUsageService cpuUsageService;

  @Test
  void 지정한_시간_구간의_분_단위_CPU_사용률을_조회할수_있다() {
    // given
    LocalDateTime startDateTime = DataSQLGenerator.START_DATE;
    LocalDateTime endDateTime = startDateTime.plusMinutes(3);

    // when
    List<CpuUsage> cpuUsageList = cpuUsageService.findCpuUsage(startDateTime, endDateTime);

    // then
    assertThat(cpuUsageList).hasSize(3);

  }
}