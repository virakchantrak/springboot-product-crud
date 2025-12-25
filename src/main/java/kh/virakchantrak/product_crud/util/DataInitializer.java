package kh.virakchantrak.product_crud.util;

import kh.virakchantrak.product_crud.entity.PermissionEntity;
import kh.virakchantrak.product_crud.entity.RoleEntity;
import kh.virakchantrak.product_crud.entity.UserEntity;
import kh.virakchantrak.product_crud.repository.PermissionRepository;
import kh.virakchantrak.product_crud.repository.RoleRepository;
import kh.virakchantrak.product_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final PermissionRepository permissionRepository;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    // ---- PERMISSIONS ----
    PermissionEntity brandWrite = savePermission("brand:write");
    PermissionEntity brandRead = savePermission("brand:read");
    PermissionEntity modelWrite = savePermission("model:write");
    PermissionEntity modelRead = savePermission("model:read");

    // ---- ROLES ----
    RoleEntity adminRole = saveRole("ADMIN", Set.of(brandWrite, brandRead, modelWrite, modelRead));
    RoleEntity staffRole = saveRole("STAFF", Set.of(brandRead, modelRead));

    // ---- USERS ----
    saveUser("admin", "admin123", adminRole);
    saveUser("staff", "staff123", staffRole);

    System.out.println("✅ Database initialized with test users!");
  }

  private PermissionEntity savePermission(String name) {
    return permissionRepository
        .findByName(name)
        .orElseGet(() -> permissionRepository.save(new PermissionEntity(null, name)));
  }

  private RoleEntity saveRole(String name, Set<PermissionEntity> permissions) {
    return roleRepository
        .findByName(name)
        .orElseGet(
            () -> {
              RoleEntity role = new RoleEntity();
              role.setName(name);
              role.setPermissions(permissions);
              return roleRepository.save(role);
            });
  }

  private void saveUser(String username, String rawPassword, RoleEntity role) {
    userRepository
        .findByUsername(username)
        .orElseGet(
            () -> {
              UserEntity user = new UserEntity();
              user.setUsername(username);
              user.setPassword(passwordEncoder.encode(rawPassword));
              user.setFirstName(username);
              user.setLastName("Test");
              user.setEnabled(true);
              user.setAccountNonExpired(true);
              user.setAccountNonLocked(true);
              user.setCredentialsNonExpired(true);
              user.setRoles(Set.of(role));
              return userRepository.save(user);
            });
  }
}
