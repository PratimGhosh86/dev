package com.proto.merchlend.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author pratim
 *
 */
// jackson configuration
@Configuration
public class JacksonConfigurer {

  private static final Logger LOG =
      LoggerFactory.getLogger(JacksonConfigurer.class);

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
    LOG.info("Configured Jackson object mapper");
    return objectMapper;
  }

  @Primary
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    LOG.info("Configuring Jackson object mapper builder");
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
