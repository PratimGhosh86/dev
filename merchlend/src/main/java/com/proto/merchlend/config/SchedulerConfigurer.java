package com.proto.merchlend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfigurer {

  private static final Logger LOG =
      LoggerFactory.getLogger(SchedulerConfigurer.class);

  @Value("${scheduler.pool.size:1}")
  private Integer poolSize;

  @Bean(destroyMethod = "shutdown")
  public ThreadPoolTaskScheduler taskScheduler() {
    LOG.info("Configuring thread pool for schedules tasks [size: {}]",
        poolSize);
    return new ThreadPoolTaskScheduler() {
      private static final long serialVersionUID = 1L;
      {
        setPoolSize(poolSize);
        setDaemon(true);
        setThreadNamePrefix("SchedulerPool-");
        setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
      }
    };
  }

}
