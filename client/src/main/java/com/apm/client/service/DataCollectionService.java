package com.apm.client.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataCollectionService {
  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
  private final InfluxDBService influxDBService;

  public void startDataCollection(String org) {
    executorService.scheduleAtFixedRate(() -> influxDBService.saveMetricsBy(org), 0, 1, TimeUnit.MINUTES);
  }

  public void stopDataCollection() {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      executorService.shutdownNow();
    }
  }
}
