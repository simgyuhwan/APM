package com.terra.cpu.service;

import com.influxdb.client.InfluxDBClient;
import com.terra.cpu.domain.Metric;
import com.terra.cpu.domain.MetricType;
import java.util.List;
import java.util.function.Function;
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
  public <T, R extends Metric> List<R> queryMetrics(MetricType type, String start, String stop,
      Function<T, R> mapper) {

    return null;
  }
}
