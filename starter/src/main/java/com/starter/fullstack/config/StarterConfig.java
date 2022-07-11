package com.starter.fullstack.config;

import com.starter.fullstack.dao.InventoryDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Starter Configuration.
 */
@Configuration
public class StarterConfig {

  @Bean
  public InventoryDAO inventoryDAO(MongoTemplate mongoTemplate) {
    return new InventoryDAO(mongoTemplate);
  }
}
