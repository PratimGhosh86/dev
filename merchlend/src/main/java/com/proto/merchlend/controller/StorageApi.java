package com.proto.merchlend.controller;

import java.io.IOException;
import java.util.List;
import com.proto.merchlend.model.FileStatus;
import com.proto.merchlend.service.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;

/**
 * @author pratim
 *
 */
@RestController
@Api(tags = "Storage Controller",
    description = "Controller to upload/download files")
public class StorageApi {

  @Autowired
  private StorageManager storage;

  @PostMapping(path = "/upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public org.springframework.hateoas.Resource<FileStatus> uploadFile(
      @RequestParam("merchantLoanOfferId") final String merchantLoanOfferId,
      @RequestParam("file") final MultipartFile file) throws IOException {
    return new org.springframework.hateoas.Resource<FileStatus>(
        storage.save(merchantLoanOfferId, file));
  }

  @PostMapping(path = "/upload/all",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public org.springframework.hateoas.Resources<FileStatus> uploadFiles(
      @RequestParam("merchantLoanOfferId") final String merchantLoanOfferId,
      @RequestParam("file") final List<MultipartFile> files) {
    return new org.springframework.hateoas.Resources<FileStatus>(
        storage.saveAll(merchantLoanOfferId, files));
  }

  @GetMapping("/list")
  public org.springframework.hateoas.Resources<FileStatus> listFiles(
      @RequestParam("merchantLoanOfferId") final String merchantLoanOfferId)
      throws IOException {
    return new org.springframework.hateoas.Resources<FileStatus>(
        storage.listUploadedFiles(merchantLoanOfferId));
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> download(
      @RequestParam("merchantLoanOfferId") final String merchantLoanOfferId,
      @RequestParam("file") final String file) throws IOException {
    Resource requestedFile = storage.download(merchantLoanOfferId, file);
    return ResponseEntity.ok()
        .header("Content-disposition", "inline; filename=" + file)
        .contentLength(requestedFile.contentLength())
        .contentType(MediaType
            .parseMediaType(storage.getMimeType(merchantLoanOfferId, file)))
        .body(requestedFile);
  }

}
