package com.dev.boot.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import com.dev.boot.entity.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;

/**
 * @author pratim
 *
 */
@Api(tags = "Employee Entity")
@RepositoryRestResource(path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  /**
   * @param lastName
   * @param sortOrder
   * @return list of {@link Employee}
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "sort", allowMultiple = true,
      dataType = "string", paramType = "query",
      value = "Sorting criteria in the format: property(,asc|desc). "
          + "Default sort order is ascending. Multiple sort criteria are supported.")})
  @Transactional(readOnly = true)
  List<Employee> findByLastName(
      @ApiParam(value = "employee last name",
          required = true) @Param("lastName") String lastName,
      Sort sortOrder);

  /**
   * @param lastName
   * @param sortOrder
   * @return list of {@link Employee}
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "sort", allowMultiple = true,
      dataType = "string", paramType = "query",
      value = "Sorting criteria in the format: property(,asc|desc). "
          + "Default sort order is ascending. Multiple sort criteria are supported.")})
  @Query("select new Employee(firstName) from Employee e where e.lastName = :lastName")
  @Transactional(readOnly = true)
  List<Employee> findFirstNameByLastName(
      @ApiParam(value = "employee last name",
          required = true) @Param("lastName") String lastName,
      Sort sortOrder);

  /**
   * @param lastNameFilter
   * @param sortOrder
   * @return list of {@link Employee}
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "sort", allowMultiple = true,
      dataType = "string", paramType = "query",
      value = "Sorting criteria in the format: property(,asc|desc). "
          + "Default sort order is ascending. Multiple sort criteria are supported.")})
  @Transactional(readOnly = true)
  List<Employee> findByLastNameContainingIgnoreCase(
      @ApiParam(value = "characters contained in employee last name",
          required = true) @Param("lastNameFilter") String lastNameFilter,
      Sort sortOrder);

  /**
   * @param lastNameFilter
   * @return count of deleted {@link Employee}
   */
  @Modifying
  @Transactional
  Long deleteByLastNameContaining(String lastNameFilter);
}
