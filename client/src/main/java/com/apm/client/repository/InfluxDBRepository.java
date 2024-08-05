package com.apm.client.repository;

import com.apm.client.domain.Metric;
import com.influxdb.client.InfluxDBClient;
import org.springframework.stereotype.Repository;

@Repository
public class InfluxDBRepository {
  public void saveMetric(InfluxDBClient influxDBClient, Metric metric) {
    influxDBClient.getWriteApiBlocking().writePoint(metric.toPoint());
  }

}
