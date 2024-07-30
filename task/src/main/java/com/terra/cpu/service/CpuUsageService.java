package com.terra.cpu.service;

import com.terra.cpu.constants.ApiTimeLimits;
import com.terra.cpu.constants.LimitDateType;
import com.terra.cpu.domain.CpuStats;
import com.terra.cpu.exception.InvalidDateRangeException;
import com.terra.cpu.repository.CpuUsageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;

  @Transactional(readOnly = true)
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

  @Transactional(readOnly = true)
  public List<CpuStats> findCpuUsage(LocalDateTime startDateTime,
      LocalDateTime endDateTime, LimitDateType dateType) {
    validateDateRange(startDateTime, endDateTime);
    LocalDateTime effectiveStartDateTime = getEffectiveStartDateTime(startDateTime,
        dateType.getLimitDateTime());

    return switch (dateType) {
      case HOURS -> cpuUsageRepository
          .findHourlyCpuUsageStatsByDate(effectiveStartDateTime, endDateTime);
      case DAYS -> cpuUsageRepository
          .findDailyCpuUsageStatsByDate(effectiveStartDateTime, endDateTime);
    };
  }

  private void validateDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    if (startDateTime == null || endDateTime == null) {
      throw new InvalidDateRangeException("startDateTime and endDateTime must not be null");
    }
    if (startDateTime.isAfter(endDateTime)) {
      throw new InvalidDateRangeException("startDateTime must be before endDateTime");
    }
  }

  private LocalDateTime getEffectiveStartDateTime(LocalDateTime startDateTime,
      LocalDateTime limitDateTime) {
    return startDateTime.isBefore(limitDateTime) ? limitDateTime : startDateTime;
  }
}

