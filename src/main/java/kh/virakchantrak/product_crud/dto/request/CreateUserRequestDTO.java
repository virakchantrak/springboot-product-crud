package kh.virakchantrak.product_crud.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<String> roles;
}
