package com.demo.keep.config.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter
    extends BasicAuthenticationFilter {

  private final String key;

  public JwtAuthenticationFilter(final AuthenticationManager authManager,
      final String key) {
    super(authManager);
    this.key = key;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req,
      HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(req, res);
      return;
    }
    String token = req.getHeader("Authorization");
    String uid = "";
    try {
      // parse the token.
      Jws<Claims> jws =
          Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
              .parseClaimsJws(token.replace("Bearer ", ""));
      uid = Optional.ofNullable(jws.getBody().get("uid")).orElse("").toString();
      if (uid.length() == 0)
        throw new JwtException("Token not recognized");
    } catch (JwtException ex) {
      throw new IOException(ex.getMessage());
    }
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(uid, null, new ArrayList<>());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

}
