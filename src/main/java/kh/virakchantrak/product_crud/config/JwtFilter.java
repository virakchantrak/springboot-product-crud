package kh.virakchantrak.product_crud.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import kh.virakchantrak.product_crud.dto.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class JwtFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    ObjectMapper mapper = new ObjectMapper();
    try {
      LoginRequestDTO loginRequest =
          mapper.readValue(request.getInputStream(), LoginRequestDTO.class);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(), loginRequest.getPassword());
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    String secreteKey = "abcdefghijklmnopqrstuvwxyz+abcdefghijklmnopqrstuvwxyz";
    String token =
        Jwts.builder()
            .setSubject(authResult.getName())
            .setIssuedAt(new Date())
            .claim("authorities", authResult.getAuthorities())
            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
            .setIssuer("product-crud")
            .signWith(Keys.hmacShaKeyFor(secreteKey.getBytes()))
            .compact();

    response.setHeader("Authorization", "Bearer" + token);
  }
}
