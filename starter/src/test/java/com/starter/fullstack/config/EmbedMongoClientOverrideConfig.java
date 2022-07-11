package com.starter.fullstack.config;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * Configuration for integration tests
 */
@EnableAutoConfiguration
@Configuration
@ActiveProfiles("test")
public class EmbedMongoClientOverrideConfig {

  @Bean
  public MongoClient mongoClient() {
    return new MongoClient("127.0.0.1", Integer.parseInt(System.getProperty("mongo.port")));
  }
}

