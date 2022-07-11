package com.starter.fullstack;

import java.time.Clock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main Application.
 */
@SpringBootApplication
public class StarterApp {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }

  /**
   * Main.
   * @param args args.
   */
  public static void main(String[] args) {
    SpringApplication.run(StarterApp.class, args);
  }
}
