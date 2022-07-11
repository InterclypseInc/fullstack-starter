package com.starter.fullstack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("com.starter.fullstack")
public class WebSecurityProperties {
  private String allowedOrigins = "http://localhost:3000";
}
