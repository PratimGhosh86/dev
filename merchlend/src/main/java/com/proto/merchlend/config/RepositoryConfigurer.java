package com.proto.merchlend.config;

import com.proto.merchlend.entity.Lender;
import com.proto.merchlend.entity.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * @author pratim
 *
 */
// configure spring boot rest to return jpa @id column
@Configuration
public class RepositoryConfigurer extends RepositoryRestConfigurerAdapter {

  private static final Logger LOG =
      LoggerFactory.getLogger(RepositoryConfigurer.class);

  @Override
  public void
      configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Lender.class);
    config.exposeIdsFor(Offer.class);
    super.configureRepositoryRestConfiguration(config);
    LOG.info("Configured Spring data repository rest configuration");
  }

}
