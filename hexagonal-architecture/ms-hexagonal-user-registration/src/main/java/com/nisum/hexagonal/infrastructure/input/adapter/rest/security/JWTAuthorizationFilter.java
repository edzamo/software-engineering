package com.nisum.hexagonal.infrastructure.input.adapter.rest.security;

import static com.nisum.hexagonal.infrastructure.input.adapter.rest.security.Constans.HEADER_AUTHORIZACION_KEY;
import static com.nisum.hexagonal.infrastructure.input.adapter.rest.security.Constans.SUPER_SECRET_KEY;
import static com.nisum.hexagonal.infrastructure.input.adapter.rest.security.Constans.TOKEN_BEARER_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private Claims parseClaimsFromToken(String jwtToken) {
    // The modern builder pattern for the parser is more streamlined.
    // Using verifyWith is the recommended approach over the deprecated setSigningKey.
    SecretKey key = Keys.hmacShaKeyFor(SUPER_SECRET_KEY.getBytes());
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
  }

  private void setAuthentication(Claims claims) {
    List<String> authorities = (List<String>) claims.get("authorities");

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  private boolean hasAuthorizationBearer(HttpServletRequest request) {
    String authenticationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
    if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX)) {
      return false;
    }
    return true;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      if (hasAuthorizationBearer(request)) {
        String token = request.getHeader(HEADER_AUTHORIZACION_KEY).replace(TOKEN_BEARER_PREFIX, "");
        Claims claims = parseClaimsFromToken(token);
        if (claims.get("authorities") != null) {
          setAuthentication(claims);
        } else {
          SecurityContextHolder.clearContext();
        }
      } else {
        SecurityContextHolder.clearContext();
      }
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      return;
    }
  }

}