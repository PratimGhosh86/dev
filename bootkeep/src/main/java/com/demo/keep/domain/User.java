package com.demo.keep.domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.hash.Hashing;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.ResourceSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RestResource
@Entity
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
@Table(name = "user")
public class User extends ResourceSupport {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, updatable = false)
  private Long uid;

  @Column(name = "firstname", insertable = true, updatable = true,
      nullable = true)
  private String firstName;

  @Column(name = "last", insertable = true, updatable = true,
      nullable = true)
  private String lastName;

  @JsonProperty(access = Access.WRITE_ONLY)
  @JsonDeserialize(using = PasswordHasher.class)
  @Column(name = "password", insertable = true, updatable = true,
      nullable = true)
  private String password;

  public static class PasswordHasher extends JsonDeserializer<String> {

    private static final Logger LOG =
        LoggerFactory.getLogger(PasswordHasher.class);

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      String hashed = "";
      JsonToken current = jp.getCurrentToken();
      if (JsonToken.VALUE_STRING.equals(current)) {
        LOG.info("{}", jp.getText());
        hashed = Hashing.sha512()
            .hashString(jp.getText(), StandardCharsets.UTF_8).toString();
      }
      return hashed;
    }

    @Override
    public String getNullValue() {
      return "";
    }
  }

}
