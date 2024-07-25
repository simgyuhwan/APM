package com.terra.cpu.repository;

import com.terra.cpu.common.influxdb.api.InfluxDBApi;
import com.terra.cpu.common.influxdb.query.InfluxDBQuery;
import com.terra.cpu.common.influxdb.query.MeasurementType;
import com.terra.cpu.common.influxdb.query.TimeRange;
import com.terra.cpu.common.influxdb.repository.InfluxDBRepository;
import com.terra.cpu.domain.CpuEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CpuRepository implements InfluxDBRepository<CpuEntity> {

  private final InfluxDBApi<CpuEntity> influxDBApi;

  @Value("${influxdb.bucket}")
  private String bucket;

  @Override
  public List<CpuEntity> selectAll() {
    return null;
  }

  @Override
  public List<CpuEntity> selectByRange(TimeRange timeRange) {
    InfluxDBQuery query = new InfluxDBQuery(bucket, timeRange, MeasurementType.CPU);
    return influxDBApi.select(query, CpuEntity.class);
  }
}
