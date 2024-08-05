package com.apm.client.controller;

import com.apm.client.config.InfluxDBConfig;
import com.apm.client.controller.response.Response;
import com.apm.client.service.InfluxDBService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/influxdb")
@RestController
@RequiredArgsConstructor
public class InfluxDBController {

  private final InfluxDBService influxDBService;

  @PostMapping("/clients")
  public Response connectToInfluxDB(@RequestBody @Valid InfluxDBConfig influxDBConfig) {
    influxDBService.connect(influxDBConfig);
    return new Response(true, "연결 성공");
  }

  @DeleteMapping("/clients")
  public Response closeAllClients() {
    influxDBService.closeAllClients();
    return new Response(true, "연결 종료");
  }
}
