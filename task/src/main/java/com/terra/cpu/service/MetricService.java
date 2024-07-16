package com.terra.cpu.service;

import com.terra.cpu.domain.Metric;
import java.math.BigDecimal;

public interface MetricService {

  void saveMetric(Metric metric);

  BigDecimal getMetricValue(Metric.MetricType type);

}
