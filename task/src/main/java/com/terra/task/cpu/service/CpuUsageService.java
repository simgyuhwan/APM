package com.terra.task.cpu.service;

import com.terra.task.cpu.constants.ApiTimeLimits;
import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.repository.CpuUsageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;

  public List<CpuUsageRsp> findMinutesCpuUsage(LocalDateTime startDateTime,
      LocalDateTime endDateTime) {
    LocalDateTime limitDateTime = LocalDateTime.now()
        .minusDays(ApiTimeLimits.MINUTE_API_LIMIT_DAYS);

    LocalDateTime effectiveStartDateTime = getEffectiveStartDateTime(startDateTime,
        limitDateTime);

    return cpuUsageRepository
        .findMinuteCpuUsageByDate(effectiveStartDateTime, endDateTime)
        .stream()
        .map(CpuUsageRsp::from)
        .toList();
  }

  public List<CpuStats> findHoursCpuUsage(LocalDateTime startDateTime,
      LocalDateTime endDateTime) {
    LocalDateTime limitDateTime = LocalDateTime.now()
        .minusMonths(ApiTimeLimits.HOUR_API_LIMIT_MONTHS);

    LocalDateTime effectiveStartDateTime = getEffectiveStartDateTime(startDateTime, limitDateTime);

    return cpuUsageRepository.findHourlyCpuUsageStatsByDate(effectiveStartDateTime, endDateTime);
  }

  private LocalDateTime getEffectiveStartDateTime(LocalDateTime startDateTime,
      LocalDateTime limitDateTime) {
    return startDateTime.isBefore(limitDateTime) ? limitDateTime : startDateTime;
  }

  public List<CpuStats> findDailyCpuUsage(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    LocalDateTime limitDateTime = LocalDateTime.now()
        .minusYears(ApiTimeLimits.DAY_API_LIMIT_YEARS);

    LocalDateTime effectiveStartDateTime = getEffectiveStartDateTime(startDateTime, limitDateTime);

    return cpuUsageRepository
        .findDailyCpuUsageStatsByDate(effectiveStartDateTime, endDateTime);
  }
}

