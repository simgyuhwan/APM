package com.terra.task.cpu.service;

import com.sun.management.OperatingSystemMXBean;
import com.terra.task.cpu.domain.CpuUsage;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CpuUsageService {

  private final OperatingSystemMXBean systemMXBean;

  public CpuUsageService() {
    this.systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
  }

  public BigDecimal getCpuUsage() {
    double cpuUsage = systemMXBean.getProcessCpuLoad() * 100;
    return new BigDecimal(cpuUsage);
  }

  public List<CpuUsage> findCpuUsage(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    return List.of(CpuUsage.of(new BigDecimal("0")), CpuUsage.of(new BigDecimal("0")),
        CpuUsage.of(new BigDecimal("0")));
  }
}

