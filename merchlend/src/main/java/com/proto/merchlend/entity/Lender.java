package com.proto.merchlend.entity;

import java.math.BigDecimal;
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
@Table(name = "Lender")
public class Lender {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", insertable = false, updatable = false)
  private Long id;

  @Column(name = "Lender_Id", insertable = true, updatable = true,
      nullable = true)
  private Long lenderId;

  @Column(name = "Lender_Name", insertable = true, updatable = true,
      nullable = true)
  private String lenderName;

  @Column(name = "Min_Score", insertable = true, updatable = true,
      nullable = true)
  private Long minScore;

  @Column(name = "Max_Score", insertable = true, updatable = true,
      nullable = true)
  private Long maxScore;

  @Column(name = "Interest_rate", insertable = true, updatable = true,
      nullable = true)
  private BigDecimal interestRate;

  @Column(name = "Preferred", insertable = true, updatable = true,
      nullable = true)
  private String preferred;

  @Column(name = "Score", insertable = true, updatable = true, nullable = true)
  private String score;

  @Column(name = "Max_Amount", insertable = true, updatable = true,
      nullable = false)
  private Long maxAmount;

}
