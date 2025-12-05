package kh.virakchantrak.product_crud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDTO {

    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
}
