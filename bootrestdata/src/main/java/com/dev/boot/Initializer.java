package com.dev.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import de.codecentric.boot.admin.config.EnableAdminServer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author pratim
 *
 */
@Configuration
@ComponentScan(scopedProxy = ScopedProxyMode.TARGET_CLASS)
@SpringBootApplication
@EnableAdminServer
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
  public static void main(String[] args) {
    LOG.info("Loading application");
    SpringApplication.run(Initializer.class, args);
  }

  // Swagger Configuration
  @Configuration
  @EnableSwagger2
  @Profile("swagger")
  @Import({SpringDataRestConfiguration.class})
  public class Swagger2 {

    /**
     * @return {@link Docket}}
     */
    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .tags(new Tag("Employee Entity",
              "Repository for Employee entity rest api's"))
          .select()
          .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
          .build();
    }
  }

}
