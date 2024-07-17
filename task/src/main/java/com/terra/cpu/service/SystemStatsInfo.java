package com.terra.cpu.service;

import com.sun.management.OperatingSystemMXBean;
import com.terra.cpu.domain.CpuMetric;
import com.terra.cpu.domain.MemMetric;
import com.terra.cpu.repository.CpuUsageRepository;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class SystemStatsInfo {

  private final OperatingSystemMXBean systemMXBean;

  public SystemStatsInfo(CpuUsageRepository cpuUsageRepository) {
    this.systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
  }

  public BigDecimal getCpuUsage() {
    double cpuUsage = systemMXBean.getProcessCpuLoad() * 100;
    return toBigDecimal(cpuUsage);
  }

  public CpuMetric getCpuMetric() {
    double cpuLoad = systemMXBean.getCpuLoad();
    BigDecimal cpuUsage = getCpuUsage();
    return new CpuMetric(cpuUsage, new BigDecimal(cpuLoad));
  }

  public MemMetric getMemMetric() {
    long totalMem = systemMXBean.getTotalMemorySize() / (1024 * 1024);
    long freeMem = systemMXBean.getFreeMemorySize() / (1024 * 1024);
    long usedMem = totalMem - freeMem;
    double usedPercentage = ((double) usedMem / totalMem) * 100;
    return new MemMetric(toBigDecimal(totalMem), toBigDecimal(usedMem), toBigDecimal(freeMem),
        toBigDecimal(usedPercentage));
  }

  private BigDecimal toBigDecimal(double value) {
    return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
  }
}
