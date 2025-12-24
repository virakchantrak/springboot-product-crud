package kh.virakchantrak.product_crud.config;

import kh.virakchantrak.product_crud.config.jwt.JwtLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable())
        .addFilter(new JwtLoginFilter(authenticationManager()))
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated())
        .headers(
            headers ->
                headers.frameOptions(frame -> frame.sameOrigin())) // Required for H2 UI frames
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Autowired
  void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(getAuthenticationProvider());
  }

  @Bean
  public AuthenticationProvider getAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider =
        new DaoAuthenticationProvider(userDetailsService);
    return authenticationProvider;
  }
}
