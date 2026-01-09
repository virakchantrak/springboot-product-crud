package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.common.conts.ErrorCode;
import kh.virakchantrak.product_crud.common.pagination.PaginationHelper;
import kh.virakchantrak.product_crud.common.pagination.PaginationResponseDTO;
import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.exception.ResourceNotFoundException;
import kh.virakchantrak.product_crud.mapper.BrandMapper;
import kh.virakchantrak.product_crud.repository.BrandRepository;
import kh.virakchantrak.product_crud.service.BrandService;
import kh.virakchantrak.product_crud.utils.BrandFilter;
import kh.virakchantrak.product_crud.utils.BrandFilterParser;
import kh.virakchantrak.product_crud.utils.BrandSpec;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final BrandFilterParser brandFilterParser;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {

        Optional<BrandEntity> existByName = brandRepository.findByName(requestDTO.getName());
        if (existByName.isPresent()) {
            throw new ApiException(ErrorCode.C001);
        }

        BrandEntity brandEntity = brandMapper.toEntity(requestDTO);
        BrandEntity saved = brandRepository.save(brandEntity);
        return brandMapper.toResponse(saved);
    }

    @Override
    public BrandResponseDTO findBrandById(String id) {

        BrandEntity brandEntity = brandRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Brand", Long.valueOf(id)));
        return brandMapper.toResponse(brandEntity);
    }

    @Override
    public BrandResponseDTO updateBrandById(String id, BrandRequestDTO requestDTO) {

        BrandEntity brandEntity = brandRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Brand", Long.valueOf(id)));

        Optional<BrandEntity> existByName = brandRepository.findByName(requestDTO.getName());
        if (existByName.isPresent() && !existByName.get().getId().equals(brandEntity.getId())) {
            throw new ApiException(HttpStatus.CONFLICT, "Name already exist with: " + requestDTO.getName());
        }

        brandEntity.setName(requestDTO.getName());

        BrandEntity saved = brandRepository.save(brandEntity);
        return brandMapper.toResponse(saved);
    }

    @Override
    public String deleteBrandById(String id) {
        BrandEntity brandEntity = brandRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Brand", Long.valueOf(id)));
        brandRepository.delete(brandEntity);
        return "Brand deleted successfully";
    }

    @Override
    public PaginationResponseDTO<BrandResponseDTO> getBrands(Map<String, String> params) {
        BrandFilter filter = brandFilterParser.from(params);
        Pageable pageable = PaginationHelper.pageable(params);
        Page<BrandEntity> page = brandRepository.findAll(new BrandSpec(filter), pageable);
       return PaginationHelper.toResponse(page, brandMapper::toResponse);
    }

}
