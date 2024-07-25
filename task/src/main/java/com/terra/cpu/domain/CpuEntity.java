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
@InfluxMeasurement(name = "cpu")
public class CpuEntity implements InfluxEntity {

  @InfluxColumn(name = "cpuUsage")
  private BigDecimal cpuUsage;

  @InfluxColumn(name = "cpuLoad")
  private BigDecimal cpuLoad;

  @InfluxColumn(name = "date")
  private String date;

}
