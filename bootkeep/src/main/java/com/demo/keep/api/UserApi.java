package com.demo.keep.api;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import com.demo.keep.domain.Token;
import com.demo.keep.domain.Token.TokenBuilder;
import com.demo.keep.domain.User;
import com.demo.keep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController("/api/v1")
@Slf4j
public class UserApi {

  private final Environment env;
  private final UserRepository userRepo;

  @PostMapping("/register")
  public User register(@RequestBody final User user) {
    User savedUser = User.builder().build();
    if (Optional.ofNullable(user).isPresent()) {
      savedUser = userRepo.saveAndFlush(user);
      if (log.isDebugEnabled())
        log.debug("Saved [{}]", savedUser);
    }
    return savedUser;
  }

  @PostMapping("/authenticate")
  public Token authenticate(@RequestBody final User loginUser)
      throws NoSuchAlgorithmException {
    if (!Optional.ofNullable(loginUser.getPassword()).isPresent())
      throw new AccessDeniedException("Invalid credentials");
    TokenBuilder token = Token.builder();
    log.info("Validation [{}]", loginUser);
    Optional<User> user = userRepo.findByFirstNameAndLastName(
        loginUser.getFirstName(), loginUser.getLastName());
    if (user.isPresent()) {
      log.info("Found [{}]", user.get());
      if (Objects.equals(
          loginUser.getPassword(),
          user.get().getPassword())) {
        log.info("Password matches, generating token");
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", user.get().getUid());
        claims.put("firstname", user.get().getFirstName());
        claims.put("lastname", user.get().getLastName());
        LocalDateTime expiry = LocalDate.now().plusDays(1L).atStartOfDay();
        token.expires(expiry);
        token.jwtToken(Jwts.builder()
            .setSubject(user.get().getUid().toString())
            .setId(user.get().getUid().toString())
            .setClaims(claims)
            .setIssuedAt(Date.from(LocalDate.now().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()))
            .setExpiration(Date.from(expiry
                .atZone(ZoneId.systemDefault())
                .toInstant()))
            .signWith(Keys.hmacShaKeyFor(env.getProperty("jwt.key").getBytes()),
                SignatureAlgorithm.HS256)
            .compact());
      }
    }
    return token.build();
  }

}
