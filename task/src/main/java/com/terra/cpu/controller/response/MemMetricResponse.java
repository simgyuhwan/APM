package com.terra.cpu.controller.response;

import java.math.BigDecimal;

public record MemMetricResponse(BigDecimal totalMem, BigDecimal usedMem, BigDecimal freeMem,
                                BigDecimal usedPercent, String date) {

}
