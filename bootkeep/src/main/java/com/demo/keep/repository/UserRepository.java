package com.demo.keep.repository;

import java.util.Optional;
import com.demo.keep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @ApiImplicitParams({
      @ApiImplicitParam(name = "firstName", value = "First Name",
          required = true,
          dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "lastName", value = "Last Name",
          required = true, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "Authorization", value = "Bearer ",
          required = true, dataType = "string", paramType = "header")
  })
  public Optional<User> findByFirstNameAndLastName(String firstName,
      String lastName);

}
