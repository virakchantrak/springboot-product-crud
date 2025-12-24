package kh.virakchantrak.product_crud.service.impl;

import java.util.List;
import java.util.Optional;
import kh.virakchantrak.product_crud.common.RoleEnum;
import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceFakeImpl implements UserService {
  private final List<AuthUser> users;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Optional<AuthUser> findUserByUsername(String username) {
    List<AuthUser> users =
        List.of(
            new AuthUser(
                RoleEnum.ADMIN.getAuthorities(),
                "virak",
                passwordEncoder.encode("virak123"),
                true,
                true,
                true,
                true),
            new AuthUser(
                RoleEnum.STAFF.getAuthorities(),
                "saley",
                passwordEncoder.encode("saley123"),
                true,
                true,
                true,
                true));

    return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
  }
}
