package com.terra.cpu.common.influxdb.repository;

import com.terra.cpu.common.influxdb.query.TimeRange;
import java.util.List;

public interface InfluxDBRepository<T> {

  List<T> selectAll();

  List<T> selectByRange(TimeRange timeRange);
}

