package com.terra.cpu.domain;

import com.influxdb.client.write.Point;

public interface Metric {

  Point toPont();
}
