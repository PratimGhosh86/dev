package com.proto.merchlend.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import com.itextpdf.text.DocumentException;
import com.proto.merchlend.model.FileStatus;
import com.proto.merchlend.service.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@RestController
@Api(tags = "Report Controller",
    description = "Controller to generate reports")
public class ReportApi {

  @Autowired
  private ReportManager reports;

  @GetMapping(path = "/report/merchant")
  public org.springframework.hateoas.Resource<FileStatus> uploadFile(
      @RequestParam("merchantLoanOfferId") final Long merchantLoanOfferId)
      throws IOException, DocumentException, NoSuchAlgorithmException {
    return new org.springframework.hateoas.Resource<FileStatus>(
        reports.generateReportForMerchant(merchantLoanOfferId));
  }

}
