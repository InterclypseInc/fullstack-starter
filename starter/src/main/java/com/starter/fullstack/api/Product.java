package com.starter.fullstack.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Version;

/**
 * Product -- Used to Categorize Inventory.
 */
@Data
public class Product {
  private String id;
  @Version
  private long version;
  @NotBlank(message = "Product Name was not provided.")
  private String name;
}
