package com.terra.cpu.domain;


import com.terra.cpu.common.influxdb.annotation.InfluxColumn;
import com.terra.cpu.common.influxdb.annotation.InfluxMeasurement;
import com.terra.cpu.common.influxdb.repository.InfluxEntity;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@InfluxMeasurement(name = "memory")
public class MemEntity implements InfluxEntity {

  @InfluxColumn(name = "totalMem")
  private BigDecimal totalMem;

  @InfluxColumn(name = "usedMem")
  private BigDecimal usedMem;

  @InfluxColumn(name = "freeMem")
  private BigDecimal freeMem;

  @InfluxColumn(name = "usedPercent")
  private BigDecimal usedPercent;

  @InfluxColumn(name = "date")
  private String date;
}
