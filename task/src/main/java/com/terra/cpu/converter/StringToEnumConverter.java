package com.terra.cpu.converter;

import com.terra.cpu.constants.LimitDateType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, LimitDateType> {

  @Override
  public LimitDateType convert(String source) {
    return LimitDateType.valueOf(source.toUpperCase());
  }
}
