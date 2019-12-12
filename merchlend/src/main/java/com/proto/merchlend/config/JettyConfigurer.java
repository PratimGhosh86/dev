package com.proto.merchlend.config;

import java.lang.management.ManagementFactory;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pratim
 *
 */
// Jetty configuration foor HTTP redirection and JMX registration
@Configuration
public class JettyConfigurer implements EmbeddedServletContainerCustomizer {

  private static final Logger LOG =
      LoggerFactory.getLogger(JettyConfigurer.class);

  public class HttpToHttpsJettyConfiguration extends AbstractConfiguration {

    // http://wiki.eclipse.org/Jetty/Howto/Configure_SSL#Redirecting_http_requests_to_https
    @Override
    public void configure(WebAppContext context) throws Exception {
      context.setSecurityHandler(new ConstraintSecurityHandler() {
        {
          addConstraintMapping(new ConstraintMapping() {
            {
              setPathSpec("/*");
              setConstraint(new Constraint() {
                private static final long serialVersionUID = 1L;
                {
                  setDataConstraint(2);
                }
              });
            }
          });
        }
      });
      LOG.info("Configured web application context");
    }
  }

  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
    JettyEmbeddedServletContainerFactory containerFactory =
        (JettyEmbeddedServletContainerFactory) container;
    // Add a plain HTTP connector and a WebAppContext config to force redirect
    // from 8080 -> 8443
    containerFactory.addConfigurations(new HttpToHttpsJettyConfiguration());
    containerFactory.addServerCustomizers(server -> {
      server.addConnector(new ServerConnector(server) {
        {
          setPort(8080);
          addConnectionFactory(
              new HttpConnectionFactory(new HttpConfiguration() {
                {
                  setSecurePort(8443);
                  setSecureScheme("https");
                }
              }));
        }
      });
    });
    LOG.info("Configured Jetty embedded servlet container");
  }

  @Bean
  public JettyEmbeddedServletContainerFactory
      jettyEmbeddedServletContainerFactory(
          @Value("${server.port:8080}") final String port) {
    final JettyEmbeddedServletContainerFactory factory =
        new JettyEmbeddedServletContainerFactory(Integer.valueOf(port));
    factory.addServerCustomizers(new JettyServerCustomizer() {
      @Override
      public void customize(final Server server) {
        // Expose Jetty managed beans to the JMX platform server provided by
        // Spring
        final MBeanContainer mbContainer =
            new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
      }
    });
    return factory;
  }

}
