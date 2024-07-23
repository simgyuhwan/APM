package com.terra.cpu.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.terra.cpu.controller.response.CpuMetricResponse;
import com.terra.cpu.controller.response.MemMetricResponse;
import com.terra.cpu.domain.Metric;
import com.terra.cpu.domain.MetricType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InfluxDBMetricRepository implements MetricRepository {

  private final InfluxDBClient influxDBClient;

  @Value("${influxdb.bucket}")
  private String bucket;

  @Override
  public void saveMetric(Metric metric) {
    influxDBClient.getWriteApiBlocking().writePoint(metric.toPont());
  }

  @Override
  public List<CpuMetricResponse> queryCpuMetric(String start, String stop) {
    String fluxQuery = String.format(
        "from(bucket: \"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"%s\")",
        bucket, start, stop, MetricType.CPU.lowerCaseName());

    List<FluxTable> tables = influxDBClient.getQueryApi().query(fluxQuery);
    Map<String, BigDecimal> cpuUsageMap = new HashMap<>();
    Map<String, BigDecimal> cpuLoadMap = new HashMap<>();

    for (FluxTable table : tables) {
      for (FluxRecord record : table.getRecords()) {
        String time = Objects.requireNonNull(record.getTime()).toString();
        if ("cpuUsage".equals(record.getField())) {
          cpuUsageMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        } else if ("cpuLoad".equals(record.getField())) {
          cpuLoadMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        }
      }
    }

    List<CpuMetricResponse> metrics = new ArrayList<>();
    for (String time : cpuUsageMap.keySet()) {
      BigDecimal cpuUsage = cpuUsageMap.get(time);
      BigDecimal cpuLoad = cpuLoadMap.get(time);
      if (cpuLoad != null) {
        CpuMetricResponse cpuMetric = new CpuMetricResponse(cpuUsage, cpuLoad, time);
        metrics.add(cpuMetric);
      }
    }
    metrics.sort(Comparator.comparing(CpuMetricResponse::date));
    return metrics;
  }

  @Override
  public List<MemMetricResponse> queryMemMetric(String start, String stop) {
    String fluxQuery = String.format(
        "from(bucket: \"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"%s\")",
        bucket, start, stop, MetricType.MEMORY.lowerCaseName());

    List<FluxTable> tables = influxDBClient.getQueryApi().query(fluxQuery);

    Map<String, BigDecimal> totalMemMap = new HashMap<>();
    Map<String, BigDecimal> usedMemMap = new HashMap<>();
    Map<String, BigDecimal> freeMemMap = new HashMap<>();
    Map<String, BigDecimal> usedPercentMap = new HashMap<>();

    for (FluxTable table : tables) {
      for (FluxRecord record : table.getRecords()) {
        String time = Objects.requireNonNull(record.getTime()).toString();
        if ("totalMem".equals(record.getField())) {
          totalMemMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        } else if ("usedMem".equals(record.getField())) {
          usedMemMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        } else if ("freeMem".equals(record.getField())) {
          freeMemMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        } else if ("usedPercent".equals(record.getField())) {
          usedPercentMap.put(time,
              new BigDecimal(Objects.requireNonNull(record.getValue()).toString()));
        }
      }
    }

    List<MemMetricResponse> metrics = new ArrayList<>();
    for (String time : totalMemMap.keySet()) {
      BigDecimal totalMem = totalMemMap.get(time);
      BigDecimal usedMem = usedMemMap.get(time);
      BigDecimal freeMem = freeMemMap.get(time);
      BigDecimal usedPercent = usedPercentMap.get(time);
      MemMetricResponse memMetric = new MemMetricResponse(totalMem, usedMem, freeMem, usedPercent,
          time);
      metrics.add(memMetric);
    }

    metrics.sort(Comparator.comparing(MemMetricResponse::date));
    return metrics;
  }
}
