package com.proto.merchlend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.proto.merchlend.controller.StorageApi;
import com.proto.merchlend.model.FileStatus;
import com.proto.merchlend.service.util.Utilities;
import org.apache.tika.Tika;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pratim
 *
 */
@Service
public class StorageManager {

  private static final Logger LOG =
      LoggerFactory.getLogger(StorageManager.class);

  @Autowired
  private Utilities UTIL;

  public FileStatus save(final String merchantLoanOfferId,
      final MultipartFile uploadFile) throws IOException {
    final String uploadPath =
        UTIL.getStoragePath() + "\\" + merchantLoanOfferId;
    if (Files.notExists(Paths.get(uploadPath))) {
      Files.createDirectories(Paths.get(uploadPath));
      LOG.info("Created directory: [{}]", uploadPath);
    } else {
      LOG.info("Storing file at {}", uploadPath);
    }
    final String uploadFilePath =
        uploadPath + "\\" + uploadFile.getOriginalFilename();
    Files.write(
        Paths.get(uploadFilePath),
        uploadFile.getBytes(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING);
    LOG.info("File upload successful, path: [{}]", uploadFilePath);
    FileStatus status =
        FileStatus.builder().timestamp(LocalDateTime.now().toString())
            .status(HttpStatus.OK_200)
            .message("File uploaded successfully")
            .file(uploadFile.getOriginalFilename())
            .merchantLoanOfferId(merchantLoanOfferId)
            .build();
    status.add(ControllerLinkBuilder
        .linkTo(ControllerLinkBuilder.methodOn(StorageApi.class)
            .download(status.merchantLoanOfferId, status.file))
        .withSelfRel());
    return status;
  }

  public List<FileStatus> saveAll(final String merchantLoanOfferId,
      final List<MultipartFile> files) {
    return files.stream().map(file -> {
      FileStatus status = FileStatus.builder().build();
      try {
        status = save(merchantLoanOfferId, file);
      } catch (IOException e) {
        LOG.error("File upload failed, message: {}", e.getMessage());
        status =
            FileStatus.builder().timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .message("File uploaded failed")
                .file(file.getOriginalFilename())
                .build();
      }
      return status;
    }).collect(Collectors.toList());
  }

  public List<FileStatus> listUploadedFiles(final String merchantLoanOfferId)
      throws IOException {
    LOG.info("Returning uploaded files");
    final String uploadedPath =
        UTIL.getStoragePath() + "\\" + merchantLoanOfferId;
    return !Files.exists(Paths.get(uploadedPath)) ? Collections.emptyList()
        : Files.walk(Paths.get(uploadedPath))
            .filter(Files::isRegularFile)
            .peek(file -> LOG.debug("Found files: [{}]", file))
            .map(file -> {
              FileStatus status = FileStatus.builder()
                  .file(file.getFileName().toFile().getName())
                  .timestamp(LocalDateTime.now().toString())
                  .status(HttpStatus.FOUND_302)
                  .message("Found")
                  .merchantLoanOfferId(merchantLoanOfferId)
                  .build();
              try {
                status.add(ControllerLinkBuilder
                    .linkTo(
                        ControllerLinkBuilder.methodOn(StorageApi.class)
                            .download(status.merchantLoanOfferId, status.file))
                    .withSelfRel());
              } catch (IOException e) {
                LOG.error("Unable to build HATEOAS links");
              }
              return status;
            })
            .collect(Collectors.toList());
  }

  public Resource download(final String merchantLoanOfferId,
      final String file) throws IOException {
    final String uploadedFile =
        UTIL.getStoragePath() + "\\" + merchantLoanOfferId + "\\" + file;
    LOG.info("Looking up {}", uploadedFile);
    if (!Files.exists(Paths.get(uploadedFile)))
      throw new FileNotFoundException("Requested file not found");
    return new ByteArrayResource(Files.readAllBytes(Paths.get(uploadedFile)));
  }

  public String getMimeType(final String merchantLoanOfferId,
      final String file) throws IOException {
    final String uploadedFile =
        UTIL.getStoragePath() + "\\" + merchantLoanOfferId + "\\" + file;
    LOG.info("Looking up MIME type for {}", uploadedFile);
    if (!Files.exists(Paths.get(uploadedFile)))
      throw new FileNotFoundException("Requested file not found");
    return new Tika().detect(Paths.get(uploadedFile).toFile());
  }

}
