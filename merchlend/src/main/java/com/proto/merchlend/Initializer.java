package com.proto.merchlend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class Initializer {

  private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

  /**
   * @param args
   */
  public static void main(final String[] args) {
    LOG.info("Initializing application");
    SpringApplication.run(Initializer.class, args);
  }

}
