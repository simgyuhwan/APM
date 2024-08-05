package com.apm.client.domain;

import com.influxdb.client.write.Point;

public interface Metric {
  Point toPoint();
}
