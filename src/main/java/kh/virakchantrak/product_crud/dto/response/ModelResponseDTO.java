package kh.virakchantrak.product_crud.dto.response;

import lombok.Data;

@Data
public class ModelResponseDTO {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private BrandResponseDTO brand;
}
