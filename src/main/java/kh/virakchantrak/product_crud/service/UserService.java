package kh.virakchantrak.product_crud.service;

import java.util.Optional;
import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.dto.request.CreateUserRequestDTO;
import kh.virakchantrak.product_crud.dto.response.UserResponseDTO;

public interface UserService {
    Optional<AuthUser> findUserByUsername(String username);

//    UserResponseDTO createUser(CreateUserRequestDTO request);
}
