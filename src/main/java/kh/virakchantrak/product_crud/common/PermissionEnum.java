package kh.virakchantrak.product_crud.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
    BRAND_WRITE("brand:write"),
    BRAND_READ("brand:read"),
    MODEL_WRITE("model:write"),
    MODEL_READ("model:read");

    private final String description;
}
