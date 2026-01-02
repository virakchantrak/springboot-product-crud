package kh.virakchantrak.product_crud.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class UserResponseDTO {
    private String id;
    private String username;
    private Set<String> roles;
}
