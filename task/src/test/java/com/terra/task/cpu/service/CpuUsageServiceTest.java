package com.terra.task.cpu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.terra.task.TaskApplication;
import com.terra.task.cpu.constants.LimitDateType;
import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.exception.InvalidDateRangeException;
import com.terra.task.cpu.repository.CpuUsageRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@SpringBootTest(classes = TaskApplication.class)
class CpuUsageServiceTest {

  @Autowired
  CpuUsageService cpuUsageService;

  @Autowired
  CpuUsageRepository cpuUsageRepository;

  @Test
  void 일단위_CPU_사용률조회는_최근_1년_데이터만_조회한다() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime fourMonthsAgoDateTime = currentDateTime.minusYears(2);

    CpuUsage cpuUsageCurrent = CpuUsage.of(currentDateTime, BigDecimal.TEN);
    CpuUsage cpuUsageFourMonthsAgo = CpuUsage.of(fourMonthsAgoDateTime, BigDecimal.TEN);

    cpuUsageRepository.saveAll(List.of(cpuUsageCurrent, cpuUsageFourMonthsAgo));

    // when
    List<CpuStats> cpuStatsList = cpuUsageService.findCpuUsage(fourMonthsAgoDateTime,
        currentDateTime, LimitDateType.DAYS);

    // then
    assertThat(cpuStatsList).hasSize(1);
  }


  @Test
  void 지정한_날짜구간의_일단위_CPU_사용률을_조회한다() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime monthsAgeDateTime = currentDateTime.minusMonths(11);

    CpuUsage cpuUsageCurrent = CpuUsage.of(currentDateTime, BigDecimal.TEN);
    CpuUsage cpuUsageMonthsAge = CpuUsage.of(monthsAgeDateTime, BigDecimal.TEN);

    cpuUsageRepository.saveAll(List.of(cpuUsageCurrent, cpuUsageMonthsAge));

    // when
    List<CpuStats> cpuStatsList = cpuUsageService.findCpuUsage(monthsAgeDateTime,
        currentDateTime, LimitDateType.DAYS);

    // then
    assertThat(cpuStatsList).hasSize(2);
  }

  @Test
  void 시단위_CPU_사용률조회는_최근_3달_데이터만_조회한다() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime fourMonthsAgoDateTime = currentDateTime.minusMonths(4);

    CpuUsage cpuUsageCurrent = CpuUsage.of(currentDateTime, BigDecimal.TEN);
    CpuUsage cpuUsageFourMonthsAgo = CpuUsage.of(fourMonthsAgoDateTime, BigDecimal.TEN);

    cpuUsageRepository.saveAll(List.of(cpuUsageCurrent, cpuUsageFourMonthsAgo));

    // when
    List<CpuStats> cpuStatsList = cpuUsageService.findCpuUsage(fourMonthsAgoDateTime,
        currentDateTime, LimitDateType.HOURS);

    // then
    assertThat(cpuStatsList).hasSize(1);
  }

  @Test
  void 지정한_날짜구간의_시간단위_CPU_사용률을_조회한다() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime monthsAgeDateTime = currentDateTime.minusDays(25);

    CpuUsage cpuUsageCurrent = CpuUsage.of(currentDateTime, BigDecimal.TEN);
    CpuUsage cpuUsageMonthsAge = CpuUsage.of(monthsAgeDateTime, BigDecimal.TEN);

    cpuUsageRepository.saveAll(List.of(cpuUsageCurrent, cpuUsageMonthsAge));

    // when
    List<CpuStats> cpuStatsList = cpuUsageService.findCpuUsage(monthsAgeDateTime,
        currentDateTime, LimitDateType.DAYS);

    // then
    assertThat(cpuStatsList).hasSize(2);
  }

  @Test
  void 분단위_CPU_사용률조회는_최근_1주일_데이터만_제공한다() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime eightDaysAgoDateTime = currentDateTime.minusDays(8);

    CpuUsage cpuUsageToday = CpuUsage.of(currentDateTime, BigDecimal.TEN);
    CpuUsage cpuUsageEightDaysAgo = CpuUsage.of(eightDaysAgoDateTime, BigDecimal.TEN);

    cpuUsageRepository.saveAll(List.of(cpuUsageToday, cpuUsageEightDaysAgo));

    // when
    List<CpuUsageRsp> cpuUsageList = cpuUsageService.findMinutesCpuUsage(eightDaysAgoDateTime,
        currentDateTime);

    // then
    assertThat(cpuUsageList).hasSize(1);
    assertThat(cpuUsageList).extracting(CpuUsageRsp::cpuPercentage)
        .containsExactlyInAnyOrder(cpuUsageToday.getCpuPercentage());
  }

  @Test
  void 시작날짜가_종료날짜보다_뒤에_있으면_예외가_발생한다() {
    // given
    LocalDateTime endDateTime = LocalDateTime.now().minusDays(10);
    LocalDateTime startDateTime = endDateTime.plusDays(3);

    // when, then
    assertThatThrownBy(
        () -> cpuUsageService.findCpuUsage(startDateTime, endDateTime, LimitDateType.DAYS))
        .isInstanceOf(InvalidDateRangeException.class);
  }

  @ParameterizedTest
  @MethodSource("provideInvalidDateData")
  void 잘못된_날짜가_들어오면_예외가_발생한다(LocalDateTime startDate, LocalDateTime endDate) {
    assertThatThrownBy(() -> cpuUsageService.findCpuUsage(startDate, endDate, LimitDateType.DAYS))
        .isInstanceOf(InvalidDateRangeException.class);
  }

  private static Stream<Arguments> provideInvalidDateData() {
    return Stream.of(
        Arguments.of(null, LocalDateTime.now()),
        Arguments.of(LocalDateTime.now(), null),
        Arguments.of(null, null)
    );
  }

  @Test
  void 지정한_시간_구간의_분_단위_CPU_사용률을_조회한다() {
    // given
    LocalDateTime startDateTime = LocalDateTime.now().minusMinutes(3);
    LocalDateTime endDateTime = startDateTime.plusMinutes(3);

    BigDecimal expectedCpuUsage1 = new BigDecimal("10.0");
    BigDecimal expectedCpuUsage2 = new BigDecimal("30.0");
    BigDecimal expectedCpuUsage3 = new BigDecimal("80.0");

    CpuUsage cpuUsage1 = CpuUsage.of(startDateTime, expectedCpuUsage1);
    CpuUsage cpuUsage2 = CpuUsage.of(startDateTime.plusMinutes(1), expectedCpuUsage2);
    CpuUsage cpuUsage3 = CpuUsage.of(startDateTime.plusMinutes(2), expectedCpuUsage3);

    cpuUsageRepository.saveAll(List.of(cpuUsage1, cpuUsage2, cpuUsage3));

    // when
    List<CpuUsageRsp> cpuUsageList = cpuUsageService.findMinutesCpuUsage(startDateTime,
        endDateTime);

    // then
    assertThat(cpuUsageList).hasSize(3);
    assertThat(cpuUsageList).extracting(CpuUsageRsp::cpuPercentage)
        .containsExactlyInAnyOrder(
            expectedCpuUsage1,
            expectedCpuUsage2,
            expectedCpuUsage3
        );
  }
}