package com.proto.merchlend.config;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author pratim
 *
 */
// spring security (rather the lack of it)
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  private static final Logger LOG =
      LoggerFactory.getLogger(SecurityConfigurer.class);

  @Autowired
  private Environment ENV;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // since this is for poc purpose, allow all path access
    http.authorizeRequests().antMatchers("/**").permitAll();
    http.requiresChannel().antMatchers("/**").requiresSecure();
    // allow localhost access from different ports
    http.cors();
    // need to allow H2 console
    if (Arrays.asList(ENV.getActiveProfiles()).contains("embedded")) {
      http.csrf().disable();
      http.headers().frameOptions().disable();
    }
    super.configure(http);
    LOG.info("Configured Spring HTTP security");
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
            .allowCredentials(Boolean.TRUE)
            .allowedHeaders("*")
            .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
                "OPTIONS", "TRACE");
      }
    };
  }

}
