package com.terra.cpu.controller.response;

public record RealTimeMetrics(CpuMetricResponse cpuMetricResponse,
                              MemMetricResponse memMetricResponse) {

}
