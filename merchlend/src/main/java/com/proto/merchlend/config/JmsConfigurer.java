package com.proto.merchlend.config;

import javax.jms.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfigurer {

  private static final Logger LOG =
      LoggerFactory.getLogger(JmsConfigurer.class);

  @Autowired
  private ObjectMapper objectMapper;

  // Only required due to defining myFactory in the receiver
  @Bean
  public JmsListenerContainerFactory<?> jmsFactory(
      ConnectionFactory connectionFactory,
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    factory.setErrorHandler(
        t -> LOG.error("Error while processing MQ [{}]", t.getMessage()));
    configurer.configure(factory, connectionFactory);
    LOG.info("Configured JMS factory");
    return factory;
  }

  // Serialize message content to json using TextMessage
  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter =
        new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter.setObjectMapper(objectMapper);
    LOG.info("Configured JMS message converter");
    return converter;
  }

}
