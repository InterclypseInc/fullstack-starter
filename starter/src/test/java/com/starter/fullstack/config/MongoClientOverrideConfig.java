package com.starter.fullstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Points Spring Data Mongo at a Testcontainers MongoDB instance for integration tests.
 */
@Configuration
public class MongoClientOverrideConfig {
  private static final MongoDBContainer MONGO_DB_CONTAINER;

  static {
    DockerImageName dockerImageName = DockerImageName.parse("mongo:8");
    MONGO_DB_CONTAINER = new MongoDBContainer(dockerImageName)
      .withReuse(true);
    MONGO_DB_CONTAINER.start();
  }

  @Bean(destroyMethod = "")
  public MongoDBContainer mongoDBContainer() {
    return MONGO_DB_CONTAINER;
  }

  @Bean
  public MongoClientFactoryBean mongoClientFactoryBean(MongoDBContainer container) {
    MongoClientFactoryBean mongo = new MongoClientFactoryBean();
    mongo.setHost(container.getHost());
    mongo.setPort(container.getMappedPort(27017));
    return mongo;
  }
}
