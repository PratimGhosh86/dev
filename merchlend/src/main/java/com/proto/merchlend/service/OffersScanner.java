package com.proto.merchlend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proto.merchlend.entity.Offer;
import com.proto.merchlend.model.IncomingMerchant;
import com.proto.merchlend.repository.LenderRepository;
import com.proto.merchlend.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pratim
 *
 */
@Service
public class OffersScanner {

  private static final Logger LOG =
      LoggerFactory.getLogger(OffersScanner.class);

  @Autowired
  private Environment ENV;

  @Autowired
  private JmsTemplate jmsTemplate;

  @Autowired
  private LenderRepository lenders;

  @Autowired
  private OfferRepository offers;

  @Scheduled(fixedDelayString = "${scanner.trigger.seconds:60}000")
  @Transactional(propagation = Propagation.REQUIRED)
  protected void performScanForCsv() throws IOException {
    if (Files.notExists(Paths.get(getScanPath()))) {
      Files.createDirectories(Paths.get(getScanPath()));
      LOG.info("Created directory: [{}]", getScanPath());
    }
    LOG.info("Scanning for offers in {}", Paths.get(getScanPath()));
    try (Stream<Path> folder =
        Files.walk(Paths.get(getScanPath()))) {
      folder.filter(Files::isRegularFile)
          .filter(file -> file.toString().endsWith("csv")).distinct()
          .forEachOrdered(file -> {
            LOG.debug("Processing file: {}", file.toAbsolutePath().toString());
            try {
              // rename file to mark it as being processed
              Path processingFile =
                  Paths.get(
                      file.toAbsolutePath().toString().concat(".PROCESSING"));
              LOG.debug("Renaming from {} to {} for processing",
                  file.toAbsolutePath(), Files.move(
                      file.toAbsolutePath(), processingFile,
                      StandardCopyOption.REPLACE_EXISTING));
              LOG.info("Reading merchant information from {}",
                  processingFile.toUri());
              // process data and insert into table
              Files.newBufferedReader(processingFile).lines()
                  .filter(line -> Optional.ofNullable(line).isPresent()
                      && line.trim().length() > 0)
                  .map(merch -> {
                    LOG.info("Reading line [{}]", merch);
                    String[] mInfo = merch.split(",");
                    return IncomingMerchant.builder()
                        .merchantId(Long.valueOf(mInfo[0])).email(mInfo[1])
                        .merchantName(mInfo[2]).score(mInfo[3])
                        .avgScore(Long.valueOf(mInfo[4])).build();
                  }).forEach(merch -> {
                    LOG.debug("Processing merchant {}", merch);
                    LOG.info(
                        "looking up offers for {} from lenders for score: [{}]",
                        merch.getMerchantName(), merch.getAvgScore());
                    lenders.findAllByAvgScore(merch.getAvgScore())
                        .parallelStream().forEach(lender -> {
                          LOG.info("Found lender: {}, interest rate: {}",
                              lender.getLenderName(),
                              lender.getInterestRate());
                          // Pushing to offer insert MQ
                          try {
                            jmsTemplate.convertAndSend("InsertNewOfferMQ",
                                Offer.builder()
                                    .merchantId(merch.getMerchantId())
                                    .merchantName(merch.getMerchantName())
                                    .email(merch.getEmail())
                                    .lenderId(lender.getLenderId())
                                    .interest(lender.getInterestRate())
                                    .eligibleAmount(
                                        Optional
                                            .ofNullable(lender.getMaxAmount())
                                            .orElse(
                                                10000L + Math.abs(SecureRandom
                                                    .getInstance("SHA1PRNG")
                                                    .nextLong())))
                                    .offerDate(LocalDate.now())
                                    // set a default expiry date of 30 days
                                    .offerEndDate(LocalDate.now()
                                        .plusDays(Long.parseLong(
                                            ENV.getProperty(
                                                "offer.expiry.days"))))
                                    .build());
                          } catch (JmsException | NumberFormatException
                              | NoSuchAlgorithmException e) {
                            LOG.error(e.getMessage());
                          }
                        });
                  });
              // rename file to mark it as processedFile
              Path processedFile = Paths.get(processingFile.toAbsolutePath()
                  .toString()
                  .replace(".PROCESSING", ".PROCESSED.")
                  .concat(LocalDateTime.now()
                      .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))
                      .toString()));
              LOG.debug("Processing complete. Renaming from {} to {}",
                  processingFile.toAbsolutePath(),
                  Files.move(processingFile.toAbsolutePath(),
                      processedFile, StandardCopyOption.REPLACE_EXISTING));
            } catch (IOException e) {
              LOG.error("{}: {}", e.getCause(), e.getMessage());
            }
          });
    } catch (IOException e) {
      LOG.error("{}: {}", e.getCause(), e.getMessage());
    }
  }

  @Scheduled(fixedDelayString = "${scanner.trigger.seconds:60}000")
  @Transactional(propagation = Propagation.REQUIRED)
  protected void performScanForJson() throws IOException {
    if (Files.notExists(Paths.get(getScanPath()))) {
      Files.createDirectories(Paths.get(getScanPath()));
      LOG.info("Created directory: [{}]", getScanPath());
    }
    LOG.info("Scanning for offers in {}", Paths.get(getScanPath()));
    try (Stream<Path> folder =
        Files.walk(Paths.get(getScanPath()))) {
      folder.filter(Files::isRegularFile)
          // .peek(file -> LOG.debug("Found {}", file.toUri().toString()))
          .filter(file -> file.toString().endsWith("json")).distinct()
          .forEachOrdered(file -> {
            LOG.debug("Processing file: {}", file.toAbsolutePath().toString());
            try {
              // rename file to mark it as being processed
              Path processingFile =
                  Paths.get(
                      file.toAbsolutePath().toString().concat(".PROCESSING"));
              LOG.debug("Renaming from {} to {} for processing",
                  file.toAbsolutePath(), Files.move(
                      file.toAbsolutePath(), processingFile,
                      StandardCopyOption.REPLACE_EXISTING));
              LOG.info("Reading merchant information from {}",
                  processingFile.toUri());
              // process data and insert into table
              Arrays.asList(
                  new ObjectMapper().readValue(processingFile.toFile(),
                      IncomingMerchant[].class))
                  .forEach(merch -> {
                    LOG.debug("Processing merchant {}", merch);
                    LOG.info(
                        "looking up offers for {} from lenders for score: [{}]",
                        merch.getMerchantName(), merch.getAvgScore());
                    lenders.findAllByAvgScore(merch.getAvgScore())
                        .parallelStream().forEach(lender -> {
                          LOG.info("Found lender: {}, interest rate: {}",
                              lender.getLenderName(),
                              lender.getInterestRate());
                          // Pushing to offer insert MQ
                          try {
                            jmsTemplate.convertAndSend("InsertNewOfferMQ",
                                Offer.builder()
                                    .merchantId(merch.getMerchantId())
                                    .merchantName(merch.getMerchantName())
                                    .email(merch.getEmail())
                                    .lenderId(lender.getLenderId())
                                    .interest(lender.getInterestRate())
                                    .eligibleAmount(
                                        Optional
                                            .ofNullable(lender.getMaxAmount())
                                            .orElse(
                                                10000L + Math.abs(SecureRandom
                                                    .getInstance("SHA1PRNG")
                                                    .nextLong())))
                                    .offerDate(LocalDate.now())
                                    // set a default expiry date of 30 days
                                    .offerEndDate(LocalDate.now()
                                        .plusDays(Long.parseLong(
                                            ENV.getProperty(
                                                "offer.expiry.days"))))
                                    .build());
                          } catch (JmsException | NumberFormatException
                              | NoSuchAlgorithmException e) {
                            LOG.error(e.getMessage());
                          }
                        });;
                  });
              // rename file to mark it as processedFile
              Path processedFile = Paths.get(processingFile.toAbsolutePath()
                  .toString()
                  .replace(".PROCESSING", ".PROCESSED.")
                  .concat(LocalDateTime.now()
                      .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))
                      .toString()));
              LOG.debug("Processing complete. Renaming from {} to {}",
                  processingFile.toAbsolutePath(),
                  Files.move(processingFile.toAbsolutePath(),
                      processedFile, StandardCopyOption.REPLACE_EXISTING));
            } catch (IOException e) {
              LOG.error("{}: {}", e.getCause(), e.getMessage());
            }
          });
    } catch (IOException e) {
      LOG.error("{}: {}", e.getCause(), e.getMessage());
    }
  }

  @JmsListener(destination = "InsertNewOfferMQ",
      containerFactory = "jmsFactory")
  @Transactional(propagation = Propagation.REQUIRED)
  public void saveOffer(Offer offer) {
    offers.save(offer);
    LOG.info("Inserted new offer for {}", offer.getMerchantName());
  }

  private String getScanPath() {
    return Boolean
        .parseBoolean(ENV.getProperty("files.path.use-desktop", "false"))
            ? ENV.getProperty("USERPROFILE") + "\\Desktop"
                + ENV.getProperty("files.path.scan")
            : ENV.getProperty("files.path.alternate")
                + ENV.getProperty("files.path.scan");
  }

}
