package com.terra.cpu.service;

import com.influxdb.client.InfluxDBClient;
import com.terra.cpu.domain.Metric;
import com.terra.cpu.domain.Metric.MetricType;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfluxDBMetricService implements MetricService {

  private final InfluxDBClient influxDBClient;

  @Override
  public void saveMetric(Metric metric) {
    influxDBClient.getWriteApiBlocking().writePoint(metric.toPont());
  }

  @Override
  public BigDecimal getMetricValue(MetricType type) {
    return BigDecimal.TEN;
  }
}
