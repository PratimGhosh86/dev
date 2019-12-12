package com.demo.keep.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pratim
 *
 */
// jackson configuration
@Configuration
@Slf4j
public class JacksonConfigurer {

  @Primary
  @Bean
  public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper =
        builder.createXmlMapper(Boolean.FALSE)
            .failOnUnknownProperties(Boolean.FALSE)
            .serializationInclusion(Include.ALWAYS)
            .findModulesViaServiceLoader(Boolean.TRUE).build();
    objectMapper.findAndRegisterModules();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
        Boolean.FALSE);
    log.info("Configured Jackson object mapper");
    return objectMapper;
  }

  @Primary
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    log.info("Configuring Jackson object mapper builder");
    return new Jackson2ObjectMapperBuilderCustomizer() {
      @Override
      public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.failOnUnknownProperties(Boolean.FALSE);
        builder.serializationInclusion(Include.ALWAYS);
        builder.findModulesViaServiceLoader(Boolean.TRUE);
      }
    };
  }

}
