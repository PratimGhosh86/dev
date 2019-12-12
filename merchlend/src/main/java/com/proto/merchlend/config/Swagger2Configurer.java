package com.proto.merchlend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
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
// Swagger Configuration
@Configuration
@EnableSwagger2
@Profile("swagger")
@Import({SpringDataRestConfiguration.class,
    BeanValidatorPluginsConfiguration.class})
public class Swagger2Configurer {

  private static final Logger LOG =
      LoggerFactory.getLogger(Swagger2Configurer.class);

  /**
   * @return {@link Docket}}
   */
  @Bean
  public Docket api() {
    LOG.info("Configuring Springfox docket");
    return new Docket(DocumentationType.SWAGGER_2)
        .tags(new Tag("Lender Entity", "Repository for Lender"),
            new Tag("Offer Entity", "Repository for Offers"))
        .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
        .build();
  }

}
