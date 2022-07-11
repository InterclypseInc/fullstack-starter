package com.starter.fullstack.api;

import lombok.Getter;

/**
 * Unit of Measurement.
 */
public enum UnitOfMeasurement {
  CUP("c"),
  GALLON("gal"),
  OUNCE("oz"),
  PINT("pt"),
  POUND("lb"),
  QUART("qt");

  @Getter
  private final String abbreviation;

  /**
   * Default Constructor.
   * @param abbreviation abbreviation.
   */
  UnitOfMeasurement(String abbreviation) {
    this.abbreviation = abbreviation;
  }
}
