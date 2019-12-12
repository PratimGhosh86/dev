package com.proto.merchlend.repository.test;

import com.proto.merchlend.repository.LenderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pratim
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@Transactional
public class LenderRepositoryTests {

  private static final Logger LOG =
      LoggerFactory.getLogger(LenderRepositoryTests.class);

  @Autowired
  private LenderRepository LENDER;

  @Test
  public void findAllLenders() {
    LOG.info("============looking up all available Lenders");
    LENDER.findAll().forEach(lender -> LOG.info("detail: {}", lender));
  }

}
