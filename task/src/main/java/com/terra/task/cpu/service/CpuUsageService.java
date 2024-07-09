package com.terra.task.cpu.service;

import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.repository.CpuUsageRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;

  public List<CpuUsage> findCpuUsage(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    return List.of(CpuUsage.of(new BigDecimal("0")), CpuUsage.of(new BigDecimal("0")),
        CpuUsage.of(new BigDecimal("0")));
  }
}

