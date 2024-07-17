package com.terra.cpu.service;

import com.terra.cpu.domain.Metric;
import com.terra.cpu.domain.MetricType;
import java.util.List;
import java.util.function.Function;

public interface MetricService {

  void saveMetric(Metric metric);

  <T, R extends Metric> List<R> queryMetrics(MetricType type, String start, String stop,
      Function<T, R> mapper);
}
