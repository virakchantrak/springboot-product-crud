package kh.virakchantrak.product_crud.mapper;

import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    BrandEntity toEntity(BrandRequestDTO dto);

    BrandResponseDTO toResponse(BrandEntity entity);

    List<BrandResponseDTO> toResponse(List<BrandEntity> entities);

}
