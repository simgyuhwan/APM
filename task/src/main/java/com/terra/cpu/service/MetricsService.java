package com.terra.cpu.service;

import com.terra.cpu.common.influxdb.query.TimeRange;
import com.terra.cpu.domain.CpuEntity;
import com.terra.cpu.domain.MemEntity;
import com.terra.cpu.repository.CpuRepository;
import com.terra.cpu.repository.MemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsService {

  private final MemoryRepository memoryRepository;
  private final CpuRepository cpuRepository;

  public List<MemEntity> getMemMetric(String duration) {
    TimeRange range = TimeRange.ofDuration(duration);
    return memoryRepository.selectByRange(range);
  }

  public List<CpuEntity> getCpuMetric(String duration) {
    TimeRange range = TimeRange.ofDuration(duration);
    return cpuRepository.selectByRange(range);
  }
}
