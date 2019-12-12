package com.dev.boot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import com.dev.boot.repository.EmployeeRepository;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pratim
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@Transactional
public class InitializerTests {

  private static final Logger log =
      LoggerFactory.getLogger(InitializerTests.class);

  @Autowired
  private Environment env;

  @Autowired
  private EmployeeRepository empRepo;

  /**
   *
   */
  @Test
  public void findAllEmployees() {
    // fetch all employees
    log.info("employees found with findAll():");
    log.info("-------------------------------");
    empRepo.findAll(new Sort(new Sort.Order(Direction.ASC, "firstName"),
        new Sort.Order(Direction.ASC, "lastName")))
        .forEach(emp -> log.info(emp.toString()));
    log.info("-------------------------------");
  }

  /**
   *
   */
  @Test
  public void findEmployeeByRandomId() {
    // fetch an individual employee by ID
    Long randomID = ThreadLocalRandom.current().nextLong(1, empRepo.count());
    log.info("employee found with findOne({}):", randomID);
    log.info("--------------------------------");
    Optional.ofNullable(empRepo.findOne(randomID)).ifPresent(emp -> {
      log.info(emp.toString());
    });
    log.info("-------------------------------");
  }

  /**
   *
   */
  @Test
  public void findEmployeeByLastName() {
    // fetch employee by last name
    log.info("employee found with findByLastName('Bauer'):");
    log.info("--------------------------------------------");
    empRepo
        .findByLastName("Bauer",
            new Sort(new Sort.Order(Direction.ASC, "firstName"),
                new Sort.Order(Direction.ASC, "lastName")))
        .forEach(emp -> log.info(emp.toString()));
    log.info("-------------------------------");
  }

  /**
   *
   */
  @Test
  public void findEmployeesByLastNameLike() {
    // fetch employee by last name
    log.info("employee found with findByLastNameContainingIgnoreCase('s'):");
    log.info("--------------------------------------------");
    empRepo
        .findByLastNameContainingIgnoreCase("s",
            new Sort(new Sort.Order(Direction.ASC, "firstName"),
                new Sort.Order(Direction.ASC, "lastName")))
        .forEach(emp -> log.info(emp.toString()));
    log.info("-------------------------------");
  }

  /**
   *
   */
  @Test
  public void findFirstNamesByLastName() {
    // fetch employee first name by last name
    log.info("employees found with findFirstNameByLastName('Bauer')");
    log.info("-------------------------------");
    empRepo
        .findFirstNameByLastName("Bauer",
            new Sort(new Sort.Order(Direction.ASC, "firstName"),
                new Sort.Order(Direction.ASC, "lastName")))
        .forEach(emp -> log.info(emp.toString()));
    log.info("-------------------------------");
  }

  /**
   *
   */
  @Test
  public void deleteByLastNameLike() {
    // delete employee by first name
    log.info("employees deleted with deleteByLastNameLike('Bau')");
    log.info("-------------------------------");
    log.info("{} employees deleted", empRepo.deleteByLastNameContaining("Bau"));
    log.info("-------------------------------");
    empRepo.findAll(new Sort(new Sort.Order(Direction.ASC, "firstName"),
        new Sort.Order(Direction.ASC, "lastName")))
        .forEach(emp -> log.info(emp.toString()));
    log.info("-------------------------------");
  }

  /**
   * @throws IOException
   */
  @Test
  public void writeAllEmployeesToFileUsingStreams() throws IOException {
    // write data to multiple files
    log.info("writing to file at {} Employees found with findAll():",
        env.getProperty("java.io.tmpdir"));
    log.info("-------------------------------");
    Path outputFile = Paths
        .get(env.getProperty("java.io.tmpdir") + "/bootrestdata-output.txt");
    Files.deleteIfExists(outputFile);
    empRepo.findAll(new Sort(new Sort.Order(Direction.ASC, "firstName"),
        new Sort.Order(Direction.ASC, "lastName"))).stream().forEach(emp -> {
          try {
            Files.write(outputFile,
                String.format("thread %s -> data %s \n\n",
                    Thread.currentThread().getName(),
                    emp.toString()).getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
          } catch (IOException ioe) {
            log.error(ExceptionUtils.getFullStackTrace(ioe));
          }
        });
    log.info("-------------------------------");
  }

  /**
   * @throws IOException
   */
  @Test
  public void writeAllEmployeesToFile() throws IOException {
    // write data to multiple files
    log.info("writing to file at {} Employees found with findOne():",
        env.getProperty("java.io.tmpdir"));
    log.info("-------------------------------");
    Path outputFile = Paths
        .get(env.getProperty("java.io.tmpdir") + "/bootrestdata-output.txt");
    // For full control use custom ThreadFactory and pass it as the second
    // parameter
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Files.deleteIfExists(outputFile);
    empRepo.findAll(new Sort(new Sort.Order(Direction.ASC, "firstName"),
        new Sort.Order(Direction.ASC, "lastName"))).parallelStream()
        .forEach(emp -> {
          log.info("thread# {} streaming {}", Thread.currentThread().getName(),
              emp);
          CompletableFuture.runAsync(() -> {
            log.info("submitting employee# {} to executor# {}", emp.getId(),
                Thread.currentThread().getName());
            try {
              Files.write(outputFile,
                  String
                      .format("thread %s -> data %s \n\n",
                          Thread.currentThread().getName(), emp)
                      .toString().getBytes(),
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ioe) {
              log.error(ExceptionUtils.getFullStackTrace(ioe));
            }
            log.info("completed operation of employee# {} by executor# {}",
                emp.getId(),
                Thread.currentThread().getName());
          }, executor);
        });
    log.info("Shutting down executor service");
    executor.shutdown();
    log.info("-------------------------------");
  }

}
