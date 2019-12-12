package com.dev.boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.rest.core.annotation.RestResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pratim
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@RestResource
@Entity
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
@Table(name = "EMPLOYEE")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID", insertable = false, updatable = false, length = 10)
  private Long id;

  @Column(name = "FIRSTNAME", insertable = true, updatable = true, length = 40)
  private String firstName;

  @Column(name = "LASTNAME", insertable = true, updatable = true, length = 40)
  private String lastName;

  @Column(name = "SALARY", insertable = true, updatable = true, length = 20)
  private Long salary;

  /**
   * @param firstName
   */
  public Employee(String firstName) {
    super();
    this.firstName = firstName;
  }

}
