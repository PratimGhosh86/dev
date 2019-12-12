package com.proto.merchlend.model;

import org.springframework.hateoas.ResourceSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author pratim
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileStatus extends ResourceSupport {

  public String timestamp;
  public int status;
  public String message;
  public String file;
  public String merchantLoanOfferId;

}
