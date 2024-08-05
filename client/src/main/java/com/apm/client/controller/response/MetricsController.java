package com.apm.client.controller.response;

import com.apm.client.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MetricsController {
  private final DataCollectionService dataCollectionService;

  @PostMapping("/orgs/{orgId}/metrics")
  public Response collectMetrics(@PathVariable("orgId") String orgId) {
    dataCollectionService.startDataCollection(orgId);
    return new Response(true, "수집 중...");
  }

  @PostMapping("/orgs/metrics/stop")
  public Response stopCollectingMetrics() {
    dataCollectionService.stopDataCollection();
    return new Response(true, "수집 중단됨.");
  }

}
