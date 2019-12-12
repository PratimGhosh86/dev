package com.demo.keep.config;

import java.util.Arrays;
import com.demo.keep.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pratim
 *
 */
// spring security (rather the lack of it)
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment ENV;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.formLogin().disable();
    // since this is for poc purpose, allow all path access
    http.authorizeRequests()
        .antMatchers("/v2/api-docs", "/configuration/ui",
            "/swagger-resources/**", "/configuration/**", "/swagger-ui.html",
            "/webjars/**", "/csrf**", "/",
            "/register**",
            "/authenticate**")
        .permitAll().anyRequest().authenticated().and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager(),
            ENV.getProperty("jwt.key")));
    http.requiresChannel().antMatchers("/**").requiresSecure();
    // allow localhost access from different ports
    http.cors();
    // disable csrf
    http.csrf().disable();
    // disable session
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // need to allow H2 console
    if (Arrays.asList(ENV.getActiveProfiles()).contains("embedded")) {
      http.headers().frameOptions().disable();
    }
    super.configure(http);
    log.info("Configured Spring HTTP security");
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfig =
        new CorsConfiguration().applyPermitDefaultValues();
    corsConfig.setAllowCredentials(Boolean.TRUE);
    corsConfig.setAllowedMethods(
        Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
            "OPTIONS", "TRACE"));
    final UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
        "/swagger-resources/**", "/configuration/**", "/swagger-ui.html",
        "/webjars/**", "/csrf", "/");
  }

}
