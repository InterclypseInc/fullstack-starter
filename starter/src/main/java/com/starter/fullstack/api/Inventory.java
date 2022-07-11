package com.starter.fullstack.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Version;

/**
 * Inventory.
 */
@Data
public class Inventory {
  private String id;
  @Version
  private long version;
  @NotBlank(message = "Inventory Name was not provided.")
  private String name;
  @NotBlank(message = "Inventory Product Type was not provided.")
  private String productType;
  private String description;
  @Min(0)
  private BigDecimal averagePrice;
  @Min(0)
  private BigDecimal amount;
  private UnitOfMeasurement unitOfMeasurement;
  private Instant bestBeforeDate;
  private boolean neverExpires;
  private List<String> availableStores;
}
