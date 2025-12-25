package kh.virakchantrak.product_crud.service;

import java.util.Optional;
import kh.virakchantrak.product_crud.config.AuthUser;

public interface UserService {
  Optional<AuthUser> findUserByUsername(String username);
}
