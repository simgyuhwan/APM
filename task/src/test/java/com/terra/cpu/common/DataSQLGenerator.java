package com.terra.cpu.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataSQLGenerator {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  public static final LocalDateTime START_DATE = LocalDateTime.of(2024, 5, 6, 0, 0);
  public static final LocalDateTime END_DATE = LocalDateTime.of(2024, 5, 8, 23, 59);

  private static final Random RANDOM = new Random();
  private static final String OUTPUT_FILE_PATH = "src/test/resources/db/data.sql";

//  public static void main(String[] args) {
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {
//      writer.write("-- data.sql\n\n");
//      writer.write("TRUNCATE TABLE cpu_usage;\n\n");
//      writer.write("INSERT INTO cpu_usage (timestamp, cpu_percentage)\nVALUES\n");
//
//      LocalDateTime currentDate = START_DATE;
//      StringBuilder values = new StringBuilder();
//
//      while (!currentDate.isAfter(END_DATE)) {
//        BigDecimal cpuUsage = generateRandomValue(RANDOM);
//        String timestamp = currentDate.format(DATE_TIME_FORMATTER);
//        values.append(String.format("('%s', %.1f),\n", timestamp, cpuUsage));
//
//        currentDate = currentDate.plusMinutes(1);
//      }
//
//      int lastCommaIndex = values.lastIndexOf(",");
//      if (lastCommaIndex != -1) {
//        values.deleteCharAt(lastCommaIndex);
//      }
//
//      writer.write(values.toString());
//      writer.write(";\n");
//
//      System.out.println("data.sql file has been generated at " + OUTPUT_FILE_PATH);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

}
