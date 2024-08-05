package com.apm.client.service;

import com.apm.client.config.InfluxDBConfig;
import com.apm.client.domain.CpuMetric;
import com.apm.client.domain.MemMetric;
import com.apm.client.repository.InfluxDBRepository;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfluxDBService {

  private final Map<String, InfluxDBClient> influxDBClients = new HashMap<>();
  private final SystemStatsInfo systemStatsInfo;
  private final InfluxDBRepository influxDBRepository;

  public void connect(InfluxDBConfig config) {
    String url = config.getUrl();
    char[] token = config.getToken().toCharArray();
    String org = config.getOrg();
    String bucket = config.getBucket();

    InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token, org, bucket);

      if (influxDBClient.ping()) {
        influxDBClients.put(config.getOrg(), influxDBClient);
      }
  }

  public void closeAllClients() {
    influxDBClients.values().forEach(InfluxDBClient::close);
    influxDBClients.clear();
  }

  public void saveMetricsBy(String org) {
    InfluxDBClient influxDBClient = influxDBClients.get(org);

    CpuMetric cpuMetric = systemStatsInfo.getCpuMetric();
    MemMetric memMetric = systemStatsInfo.getMemMetric();

    influxDBRepository.saveMetric(influxDBClient, cpuMetric);
    influxDBRepository.saveMetric(influxDBClient, memMetric);
  }
}
