package kh.virakchantrak.product_crud.service;

import kh.virakchantrak.product_crud.config.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
  Optional<AuthUser> findUserByUsername(String username);
}
