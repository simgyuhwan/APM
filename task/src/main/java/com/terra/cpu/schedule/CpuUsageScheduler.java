package com.terra.cpu.schedule;

import com.terra.cpu.domain.CpuUsage;
import com.terra.cpu.repository.CpuUsageRepository;
import com.terra.cpu.service.SystemStatsInfo;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class CpuUsageScheduler {

  private final CpuUsageRepository cpuUsageRepository;
  private final SystemStatsInfo systemStatsInfo;

  @Scheduled(fixedDelay = 60000)
  public void run() {
    BigDecimal cpuUsagePercent = systemStatsInfo.getCpuUsage();
    CpuUsage cpuUsage = CpuUsage.of(cpuUsagePercent);
    cpuUsageRepository.save(cpuUsage);
  }
}