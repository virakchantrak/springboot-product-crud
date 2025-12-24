package kh.virakchantrak.product_crud.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.entity.RoleEntity;
import kh.virakchantrak.product_crud.entity.UserEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.repository.UserRepository;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public Optional<AuthUser> findUserByUsername(String username) {
    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
    AuthUser authUser =
        AuthUser.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(getAuthorities(user.getRoles()))
            .accountNonExpired(user.isAccountNonExpired())
            .accountNonLocked(user.isAccountNonLocked())
            .credentialsNonExpired(user.isCredentialsNonExpired())
            .enabled(user.isEnabled())
            .build();
    return Optional.ofNullable(authUser);
  }

  private Set<SimpleGrantedAuthority> getAuthorities(Set<RoleEntity> roles) {
    Set<SimpleGrantedAuthority> authorities1 =
        roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toSet());
    Set<SimpleGrantedAuthority> authorities =
        roles.stream().flatMap(UserServiceImpl::getPermissions).collect(Collectors.toSet());
    authorities.addAll(authorities1);
    return authorities;
  }

  private static Stream<SimpleGrantedAuthority> getPermissions(RoleEntity role) {
    return role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getName()));
  }
}
