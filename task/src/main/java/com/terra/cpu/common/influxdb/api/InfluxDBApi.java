package com.terra.cpu.common.influxdb.api;

import com.terra.cpu.common.influxdb.query.InfluxDBQuery;
import com.terra.cpu.common.influxdb.repository.InfluxEntity;
import java.util.List;

public interface InfluxDBApi<T extends InfluxEntity> {

  List<T> select(InfluxDBQuery query, Class<T> clazz);
}

