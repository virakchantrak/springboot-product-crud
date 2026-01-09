package kh.virakchantrak.product_crud.common.conts;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@AllArgsConstructor
public enum RoleEnum {
  ADMIN(
      Set.of(
          PermissionEnum.BRAND_WRITE,
          PermissionEnum.BRAND_READ,
          PermissionEnum.MODEL_WRITE,
          PermissionEnum.MODEL_READ)),
  STAFF(Set.of(PermissionEnum.BRAND_READ, PermissionEnum.MODEL_READ));

  private final Set<PermissionEnum> permissions;

  public Set<SimpleGrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> auth =
        permissions.stream()
            .map(p -> new SimpleGrantedAuthority(p.getDescription()))
            .collect(Collectors.toSet());
    auth.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return auth;
  }
}
