package com.proto.merchlend.repository;

import java.time.LocalDate;
import java.util.List;
import com.proto.merchlend.entity.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;

/**
 * @author pratim
 *
 */
@Api(tags = "Offer Entity")
@RepositoryRestResource(path = "offer")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "*",
    methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD,
        RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.TRACE})
public interface OfferRepository extends JpaRepository<Offer, Long> {

  public List<Offer> findByMerchantId(
      @Param("merchantId") final Long merchantId, Pageable page);

  public List<Offer> findByLenderId(@Param("Lender_id") final Long lenderId,
      Pageable page);

  public List<Offer> findByOfferEndDateAfter(
      @Param("offerEndDate") @DateTimeFormat(
          pattern = "dd-MMM-yy") LocalDate offerEndDate,
      Pageable page);

}
