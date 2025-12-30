package com.nisum.hexagonal.infrastructure.input.adapter.rest.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import static com.nisum.hexagonal.infrastructure.input.adapter.rest.security.Constans.SUPER_SECRET_KEY;
import static com.nisum.hexagonal.infrastructure.input.adapter.rest.security.Constans.TOKEN_EXPIRATION_TIME;


@Configuration
public class JWTAuthtenticationConfig {

  public String getJWTToken(String username) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("ROLE_USER");

    SecretKey key = Keys.hmacShaKeyFor(SUPER_SECRET_KEY.getBytes());
    long now = System.currentTimeMillis();

    String token = Jwts
        .builder()
        .setId("ezamoraJWT")
        .setSubject(username)
        .claim("authorities",
            grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + TimeUnit.MINUTES.toMillis(TOKEN_EXPIRATION_TIME)))
        .signWith(key).compact();

    return "Bearer " + token;
  }

}