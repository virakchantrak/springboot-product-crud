package kh.virakchantrak.product_crud.mapper;

import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandMapper {

    public BrandEntity toEntity(BrandRequestDTO dto) {
        BrandEntity entity = new BrandEntity();
        entity.setName(dto.getName());
        return entity;
    }

    public BrandResponseDTO toResponse(BrandEntity entity) {
        BrandResponseDTO responseDTO = new BrandResponseDTO();
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setName(entity.getName());
        responseDTO.setCreatedAt(String.valueOf(entity.getCreatedAt()));
        responseDTO.setUpdatedAt(String.valueOf(entity.getUpdatedAt()));
        return responseDTO;
    }

    public List<BrandResponseDTO> toResponse(List<BrandEntity> entities) {
        return entities.stream()
                .map(this::toResponse)
                .toList();
    }

}
