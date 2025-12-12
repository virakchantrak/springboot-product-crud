package kh.virakchantrak.product_crud.mapper;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.dto.response.ModelResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import kh.virakchantrak.product_crud.entity.ModelEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper {

    ModelEntity toEntity(ModelRequestDTO dto);

    ModelResponseDTO toResponse(ModelEntity entity);

    BrandEntity toEntity(BrandResponseDTO responseDTO);

    List<ModelResponseDTO> toResponse(List<ModelEntity> entities);
}
