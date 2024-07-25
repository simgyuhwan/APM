package com.terra.cpu.common.influxdb.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InfluxMeasurement {

  String name();
}