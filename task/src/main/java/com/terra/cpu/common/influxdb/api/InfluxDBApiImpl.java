package com.terra.cpu.common.influxdb.api;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.terra.cpu.common.influxdb.collection.FluxTableResults;
import com.terra.cpu.common.influxdb.query.InfluxDBQuery;
import com.terra.cpu.common.influxdb.repository.InfluxEntity;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfluxDBApiImpl<T extends InfluxEntity> implements InfluxDBApi<T> {

  private final InfluxDBClient influxDBClient;

  @Override
  public List<T> select(InfluxDBQuery query, Class<T> clazz) {
    List<FluxTable> queryResults = influxDBClient.getQueryApi().query(query.toFluxQuery());
    FluxTableResults fluxTables = new FluxTableResults(queryResults);
    List<T> results = new ArrayList<>();

    int recordSize = fluxTables.getRecordSize();
    List<FluxTable> fluxTableList = fluxTables.getFluxTables();

    for (int recordIdx = 0; recordIdx < recordSize; recordIdx++) {
      try {
        T entity = createEntity(clazz);
        populateEntity(fluxTableList, recordIdx, entity, clazz);
        results.add(entity);
      } catch (Exception e) {
        log.error("InfluxDB query error", e);
      }
    }
    return results;
  }

  private T createEntity(Class<T> clazz) throws ReflectiveOperationException {
    return clazz.getDeclaredConstructor().newInstance();
  }

  private void populateEntity(List<FluxTable> fluxTableList, int recordIdx, T entity,
      Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
    for (FluxTable fluxTable : fluxTableList) {
      FluxRecord record = fluxTable.getRecords().get(recordIdx);

      // 날짜 필드 설정
      String time = Optional.ofNullable(record.getTime()).map(Object::toString).orElse(null);
      setFieldValue(entity, clazz, "date", time);

      // 필드 값 설정
      String fieldName = Optional.ofNullable(record.getValueByKey("_field")).map(Object::toString)
          .orElse(null);
      String value = Optional.ofNullable(record.getValue()).map(Object::toString).orElse(null);

      if (fieldName != null && value != null) {
        setFieldValue(entity, clazz, fieldName, value);
      }
    }
  }

  private void setFieldValue(T entity, Class<T> clazz, String fieldName, String value)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);

    if (field.getType() == BigDecimal.class) {
      field.set(entity, new BigDecimal(value));
    } else if (field.getType() == String.class) {
      field.set(entity, value);
    }
  }
}
