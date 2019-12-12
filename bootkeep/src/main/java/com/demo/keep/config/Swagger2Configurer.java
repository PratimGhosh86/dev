package com.demo.keep.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import lombok.extern.slf4j.Slf4j;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author pratim
 *
 */
// Swagger Configuration
@Configuration
@EnableSwagger2WebMvc
@Profile("swagger")
@Import({SpringDataRestConfiguration.class,
    BeanValidatorPluginsConfiguration.class})
@Slf4j
public class Swagger2Configurer {

  /**
   * @return {@link Docket}}
   */
  @Bean
  public Docket api() {
    log.info("Configuring Springfox docket");
    return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(
        Arrays.asList(new ParameterBuilder().name("Authorization")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false).build()))
        .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
        .build();
  }

}
