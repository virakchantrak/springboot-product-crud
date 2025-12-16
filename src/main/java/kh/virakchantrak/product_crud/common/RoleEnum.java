package kh.virakchantrak.product_crud.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static kh.virakchantrak.product_crud.common.PermissionEnum.BRAND_READ;
import static kh.virakchantrak.product_crud.common.PermissionEnum.BRAND_WRITE;
import static kh.virakchantrak.product_crud.common.PermissionEnum.MODEL_READ;
import static kh.virakchantrak.product_crud.common.PermissionEnum.MODEL_WRITE;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {
    ADMIN(Set.of(BRAND_WRITE, BRAND_READ, MODEL_WRITE, MODEL_READ)),
    STAFF(Set.of(BRAND_READ, MODEL_READ));

    private final Set<PermissionEnum> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = this.permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getDescription()))
                .collect(Collectors.toSet());

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + this.name());
        simpleGrantedAuthorities.add(role);
        return simpleGrantedAuthorities;
    }
}
