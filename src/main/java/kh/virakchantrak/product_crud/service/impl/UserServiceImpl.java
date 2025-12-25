package kh.virakchantrak.product_crud.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.entity.UserEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.repository.UserRepository;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Optional<AuthUser> findUserByUsername(String username) {
    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

    // Map roles & permissions to authorities
    Set<SimpleGrantedAuthority> authorities =
        user.getRoles().stream()
            .flatMap(
                role -> {
                  Set<SimpleGrantedAuthority> perms =
                      role.getPermissions().stream()
                          .map(p -> new SimpleGrantedAuthority(p.getName()))
                          .collect(Collectors.toSet());
                  perms.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                  return perms.stream();
                })
            .collect(Collectors.toSet());

    AuthUser authUser =
        AuthUser.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(authorities)
            .accountNonExpired(user.isAccountNonExpired())
            .accountNonLocked(user.isAccountNonLocked())
            .credentialsNonExpired(user.isCredentialsNonExpired())
            .enabled(user.isEnabled())
            .build();

    return Optional.of(authUser);
  }
}
