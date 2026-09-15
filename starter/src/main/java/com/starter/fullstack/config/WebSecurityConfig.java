package com.starter.fullstack.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * This configuration class sets up web security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true
)
@EnableConfigurationProperties(WebSecurityProperties.class)
public class WebSecurityConfig {

  /**
   * Define which URL paths should be secured.
   * @param http contains the url.
   * @return the filter chain.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Throwable {
    return http
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable())
      .build();
  }

  /**
   * Sets up Cors.
   * @return source.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource(WebSecurityProperties webSecurityProperties) {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList(webSecurityProperties.getAllowedOrigins()));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Content-type", "Authorization", "X-XSRF-TOKEN"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
