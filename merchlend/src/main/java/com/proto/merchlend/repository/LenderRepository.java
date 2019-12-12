package com.proto.merchlend.repository;

import java.util.List;
import com.proto.merchlend.entity.Lender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;

/**
 * @author pratim
 *
 */
@Api(tags = "Lender Entity")
@RepositoryRestResource(path = "lender")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "*",
    methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD,
        RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.TRACE})
public interface LenderRepository extends JpaRepository<Lender, Long> {

  public List<Lender> findAllByScoreIgnoreCase(@Param("score") String score);

  public Slice<Lender> findByScoreIgnoreCase(@Param("score") String score,
      Pageable page);

  @Query(
      value = "select l from Lender l where :avgScore between l.minScore and l.maxScore")
  public List<Lender> findAllByAvgScore(@Param("avgScore") Long avgScore);

  @Query(
      value = "select l from Lender l where :avgScore between l.minScore and l.maxScore")
  public Slice<Lender> findByAvgScore(@Param("avgScore") Long avgScore);
}
