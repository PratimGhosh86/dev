package com.demo.fileprocessor;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(scopedProxy = ScopedProxyMode.TARGET_CLASS)
@SpringBootApplication
@EnableAutoConfiguration
public class Initializer implements ApplicationRunner, CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

  @Autowired
  private FileProcessor file;

  public static void main(String[] args) {
    SpringApplication.run(Initializer.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (args.containsOption("file.path")
        && args.containsOption("operation"))
      file.process(args.getOptionValues("file.path").get(0),
          args.getOptionValues("operation").get(0));
    else {
      LOG.warn(
          "Either one/all parameters missing [--file.path],[--operation]");
      LOG.info("[--file.path] must point to valid file");
      LOG.info("[--operation] can either be ENCRYPT or DECRYPT");
    }
  }

  @Service
  public class FileProcessor {

    public void process(final String filePath, final String processType) {
      LOG.info("Validating and processing file [{}]", filePath);
      if (Files.exists(Paths.get(filePath))
          && Files.isRegularFile(Paths.get(filePath))) {
        LOG.info("Operation type: [{}]", processType);
        switch (processType) {
          case "ENCRYPT":
            break;
          case "DECRYPT":
            break;
          default:
            LOG.warn("No valid process type specified");
            break;
        }
      } else
        LOG.warn("Invalid file provided. Not processing [{}]", filePath);
    }
  }

}
