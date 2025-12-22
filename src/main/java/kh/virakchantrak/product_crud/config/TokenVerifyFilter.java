package kh.virakchantrak.product_crud.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class TokenVerifyFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");
    if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }

    String secreteKey = "abcdefghijklmnopqrstuvwxyz+abcdefghijklmnopqrstuvwxyz";
    String token = authorizationHeader.replace("Bearer ", "");
    Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secreteKey.getBytes()));
  }
}
