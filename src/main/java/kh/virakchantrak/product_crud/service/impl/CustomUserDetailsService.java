package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      AuthUser authUser = userService
              .findUserByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      return authUser;
  }
}
