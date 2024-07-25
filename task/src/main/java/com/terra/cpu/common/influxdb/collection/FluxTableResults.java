package com.terra.cpu.common.influxdb.collection;

import com.influxdb.query.FluxTable;
import java.util.Collections;
import java.util.List;
import org.springframework.util.Assert;

public class FluxTableResults {

  private final List<FluxTable> fluxTables;

  public FluxTableResults(List<FluxTable> fluxTables) {
    Assert.notNull(fluxTables, "fluxTables must not be null");
    this.fluxTables = fluxTables;
  }

  public int getRecordSize() {
    if (fluxTables.isEmpty()) {
      return 0;
    }
    return fluxTables.get(0).getRecords().size();
  }

  public List<FluxTable> getFluxTables() {
    return Collections.unmodifiableList(fluxTables);
  }
}
