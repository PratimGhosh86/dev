package com.proto.merchlend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.IntStream;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proto.merchlend.controller.StorageApi;
import com.proto.merchlend.entity.Offer;
import com.proto.merchlend.model.FileStatus;
import com.proto.merchlend.repository.OfferRepository;
import com.proto.merchlend.service.util.Utilities;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

/**
 * @author pratim
 *
 */
@Service
public class ReportManager {

  private static final Logger LOG =
      LoggerFactory.getLogger(ReportManager.class);

  @Autowired
  private Utilities UTIL;

  @Autowired
  private OfferRepository OFFERS;

  public FileStatus generateReportForMerchant(final Long merchantLoanOfferId)
      throws IOException, DocumentException, NoSuchAlgorithmException {
    final Offer offer = OFFERS.getOne(merchantLoanOfferId);
    final String reportPath =
        UTIL.getStoragePath() + "\\" + merchantLoanOfferId;
    if (Files.notExists(Paths.get(reportPath))) {
      Files.createDirectories(Paths.get(reportPath));
      LOG.info("Created directory: [{}]", reportPath);
    } else {
      LOG.info("Generating report at {}", reportPath);
    }
    final String reportFile =
        reportPath + "\\"
            + LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
            + ".pdf";
    LOG.info("Generating new report file {}", reportFile);
    new File(reportFile).createNewFile();
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(reportFile));
    document.open();
    // add metadata
    document.addTitle("Merchant Report");
    // add title
    document.add(new Paragraph("Name - " + offer.getMerchantName()));
    document.add(new Paragraph("Address - TCPL"));
    document.add(new Paragraph(""));
    document.add(new Paragraph("Date - " + LocalDate.now().toString()));
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    // add content
    PdfPTable table = new PdfPTable(4);
    table.setHeaderRows(1);
    table.setHorizontalAlignment(0);
    table.setWidthPercentage(100);
    table.addCell(new PdfPCell(new Phrase("Transaction ID")));
    table.addCell(new PdfPCell(new Phrase("Transaction Amount")));
    table.addCell(new PdfPCell(new Phrase("Interest")));
    table.addCell(new PdfPCell(new Phrase("Total")));
    IntStream
        .rangeClosed(0,
            Math.abs(SecureRandom.getInstance("SHA1PRNG").nextInt(10)))
        .forEach(index -> {
          try {
            table
                .addCell(
                    "TRN" + Math
                        .abs(SecureRandom.getInstance("SHA1PRNG").nextLong()));
            int amount =
                Math.abs(SecureRandom.getInstance("SHA1PRNG").nextInt(1000));
            table.addCell("$" + amount);
            table.addCell("$" + amount * offer.getInterestRate() / 100);
            table.addCell(
                "$" + amount + (amount * offer.getInterestRate() / 100));
          } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
          }
        });
    document.add(table);
    // generate download link
    document.close();
    FileStatus status =
        FileStatus.builder().timestamp(LocalDateTime.now().toString())
            .status(HttpStatus.OK_200)
            .message("Report generated successfully")
            .file(Paths.get(reportFile).getFileName().toString())
            .merchantLoanOfferId(merchantLoanOfferId.toString())
            .build();
    status.add(ControllerLinkBuilder
        .linkTo(ControllerLinkBuilder.methodOn(StorageApi.class)
            .download(status.merchantLoanOfferId, status.file))
        .withSelfRel());
    return status;
  }

}
