package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.config.AuthUser;
import kh.virakchantrak.product_crud.dto.request.CreateUserRequestDTO;
import kh.virakchantrak.product_crud.dto.response.UserResponseDTO;
import kh.virakchantrak.product_crud.entity.RoleEntity;
import kh.virakchantrak.product_crud.entity.UserEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.mapper.UserMapper;
import kh.virakchantrak.product_crud.repository.RoleRepository;
import kh.virakchantrak.product_crud.repository.UserRepository;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO request) {

        String username = request.getUsername() == null ? null : request.getUsername().trim();
        if (username == null || username.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username is required");
        }

        String password = request.getPassword();
        if (password == null || password.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        if (userRepository.existsByUsername(username)) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists: " + username);
        }

        Set<String> roleNames =
                (request.getRoles() == null || request.getRoles().isEmpty())
                        ? Set.of("STAFF")
                        : request.getRoles().stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .map(String::toUpperCase)
                        .collect(Collectors.toSet());

        List<RoleEntity> roles = roleRepository.findByNameIn(roleNames);

        Set<String> found = roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());

        Set<String> missing = new HashSet<>(roleNames);
        missing.removeAll(found);

        if (!missing.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid roles: " + missing);
        }


        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // server-controlled defaults
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        user.setRoles(new HashSet<>(roles));

        UserEntity saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }
}
