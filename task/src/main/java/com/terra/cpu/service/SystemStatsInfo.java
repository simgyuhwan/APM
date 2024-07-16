package com.terra.cpu.service;

import com.sun.management.OperatingSystemMXBean;
import com.terra.cpu.repository.CpuUsageRepository;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class SystemStatsInfo {

  private final OperatingSystemMXBean systemMXBean;

  public SystemStatsInfo(CpuUsageRepository cpuUsageRepository) {
    this.systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
  }

  public BigDecimal getCpuUsage() {
    double cpuUsage = systemMXBean.getProcessCpuLoad() * 100;
    return new BigDecimal(cpuUsage);
  }

}
