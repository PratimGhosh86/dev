package com.proto.merchlend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pratim
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomingMerchant {

  private Long merchantId;
  private String email;
  private String merchantName;
  private String score;
  private Long avgScore;

}
