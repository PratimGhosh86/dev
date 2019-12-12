package com.proto.merchlend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
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
@Table(name = "Offer")
public class Offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Merchant_loan_offer_id", insertable = false,
      updatable = false, nullable = false)
  private Long merchantLoanOfferId;

  @Column(name = "Merchant_id", insertable = true, updatable = true,
      nullable = false)
  private Long merchantId;

  @Column(name = "Lender_id", insertable = true, updatable = true,
      nullable = false)
  private Long lenderId;

  @Column(name = "Interest", insertable = true, updatable = true,
      nullable = true)
  private BigDecimal interest;

  @Column(name = "Email", insertable = true, updatable = true, nullable = true)
  private String email;

  @Column(name = "Merchant_Name", insertable = true, updatable = true,
      nullable = true)
  private String merchantName;

  @JsonFormat(pattern = "dd-MMM-yy")
  @Column(name = "Offer_Date", insertable = true, updatable = true,
      nullable = true)
  private LocalDate offerDate;

  @Column(name = "Loan_Amount_requested_by_merchant", insertable = true,
      updatable = true,
      nullable = true)
  private Long loanAmountRequestedByMerchant;

  @JsonFormat(pattern = "dd-MMM-yy")
  @Column(name = "Merchant_response_date", insertable = true, updatable = true,
      nullable = true)
  private LocalDate merchantResponseDate;

  @Column(name = "Merchant_accptance_status", insertable = true,
      updatable = true, nullable = true)
  private String merchantAccptanceStatus;

  @Column(name = "Merchant_rejection_reason", insertable = true,
      updatable = true, nullable = true)
  private String merchantRejectionReason;

  @Column(name = "Lender_acceptance_status", insertable = true,
      updatable = true, nullable = true)
  private String lenderAcceptanceStatus;

  @Column(name = "Lender_rejection_reason", insertable = true, updatable = true,
      nullable = true)
  private String lenderRejectionReason;

  @Column(name = "Lender_Approved_amount", insertable = true, updatable = true,
      nullable = true)
  private Long lenderApprovedAmount;

  @JsonProperty(access = Access.READ_ONLY)
  @Formula("(select l.Lender_Name from Lender l where l.Lender_Id = Lender_Id limit 1)")
  private String lenderName;

  @JsonProperty(access = Access.READ_ONLY)
  @Formula("(select l.Preferred from Lender l where l.Lender_Id = Lender_Id limit 1)")
  private String preferred;

  @Column(name = "Remarks", insertable = true, updatable = true,
      nullable = true)
  private String remarks;

  @Column(name = "Eligible_Amount", insertable = true, updatable = true,
      nullable = true)
  private Long eligibleAmount;

  @JsonFormat(pattern = "dd-MMM-yy")
  @Column(name = "Offer_End_Date", insertable = true, updatable = true,
      nullable = true)
  private LocalDate offerEndDate;

  @JsonProperty(access = Access.READ_ONLY)
  @Formula("(select l.Max_Amount from Lender l where l.Lender_Id = Lender_Id limit 1)")
  private Long maxAmount;

  @Column(name = "Interest_Rate", insertable = true, updatable = true,
      nullable = true)
  private Long interestRate;

  @Column(name = "Interest_Period", insertable = true, updatable = true,
      nullable = true)
  private Long interestPeriod;
}
