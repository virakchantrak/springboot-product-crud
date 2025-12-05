package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.dto.response.PaginationResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.exception.ResourceNotFoundException;
import kh.virakchantrak.product_crud.mapper.BrandMapper;
import kh.virakchantrak.product_crud.repository.BrandRepository;
import kh.virakchantrak.product_crud.service.BrandService;
import kh.virakchantrak.product_crud.spec.BrandFilter;
import kh.virakchantrak.product_crud.spec.BrandSpec;
import kh.virakchantrak.product_crud.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {

        Optional<BrandEntity> existByName = brandRepository.findByName(requestDTO.getName());
        if (existByName.isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "Name already exist with: " + requestDTO.getName());
        }

        BrandEntity brandEntity = brandMapper.toEntity(requestDTO);
        BrandEntity saved = brandRepository.save(brandEntity);
        return brandMapper.toResponse(saved);
    }

    @Override
    public BrandResponseDTO findBrandById(String id) {

        BrandEntity brandEntity = brandRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Brand", Integer.valueOf(id)));
        return brandMapper.toResponse(brandEntity);
    }

    @Override
    public BrandResponseDTO updateBrandById(String id, BrandRequestDTO requestDTO) {

        BrandEntity brandEntity = brandRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Brand", Integer.valueOf(id)));

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
                () -> new ResourceNotFoundException("Brand", Integer.valueOf(id)));
        brandRepository.delete(brandEntity);
        return "Brand deleted successfully";
    }

    @Override
    public PaginationResponseDTO<BrandResponseDTO> getBrands(Map<String, String> params) {

        // --- 1. Build filter ---
        BrandFilter filter = new BrandFilter();
        if (params.containsKey("name")) {
            filter.setName(params.get("name"));
        }
        if (params.containsKey("id")) {
            filter.setId(Long.valueOf(params.get("id")));
        }

        // --- 2. Pagination params ---
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);

        // --- 3. Specification ---
        BrandSpec spec = new BrandSpec(filter);

        // --- 4. Query DB ---
        Page<BrandEntity> page = brandRepository.findAll(spec, pageable);

        // --- 5. Map entities to DTOs ---
        List<BrandResponseDTO> data = brandMapper.toResponse(page.getContent());

        // --- 6. Build and return pagination response ---
        return PaginationResponseDTO.<BrandResponseDTO>builder()
                .data(data)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();
    }

}
