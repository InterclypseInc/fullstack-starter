package com.starter.fullstack.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * This configuration class sets up web security.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@EnableConfigurationProperties(WebSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Define which URL paths should be secured.
   * @param http contains the url.
   * @throws Exception if Exception occurs.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
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
