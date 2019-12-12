package com.proto.merchlend.service.util;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author pratim
 *
 */
@Service
public class Utilities {

  private static final Logger LOG =
      LoggerFactory.getLogger(Utilities.class);

  @Autowired
  private Environment ENV;

  private String fileStorePath = null;

  public String getStoragePath() {
    return Optional.ofNullable(fileStorePath).orElseGet(() -> {
      LOG.debug("Detecting file store path");
      fileStorePath = Boolean
          .parseBoolean(ENV.getProperty("files.path.use-desktop", "false"))
              ? ENV.getProperty("USERPROFILE") + "\\Desktop"
                  + ENV.getProperty("files.path.upload")
              : ENV.getProperty("files.path.alternate")
                  + ENV.getProperty("files.path.upload");
      return fileStorePath;
    });
  }

}
