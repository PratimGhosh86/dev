package com.demo.keep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pratim
 *
 */
@Configuration
@ComponentScan(scopedProxy = ScopedProxyMode.TARGET_CLASS)
@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true)
@Slf4j
public class Initializer {

  public static void main(String[] args) {
    log.info("Initializing application");
    SpringApplication.run(Initializer.class, args);
  }

}
