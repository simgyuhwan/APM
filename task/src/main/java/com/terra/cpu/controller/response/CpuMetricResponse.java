package com.terra.cpu.controller.response;

import java.math.BigDecimal;

public record CpuMetricResponse(BigDecimal cpuUsage, BigDecimal cpuLoad, String date) {

}
