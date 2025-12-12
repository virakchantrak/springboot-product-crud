package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.dto.response.ModelResponseDTO;
import kh.virakchantrak.product_crud.dto.response.PaginationResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import kh.virakchantrak.product_crud.entity.ModelEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.exception.ResourceNotFoundException;
import kh.virakchantrak.product_crud.mapper.ModelMapper;
import kh.virakchantrak.product_crud.repository.BrandRepository;
import kh.virakchantrak.product_crud.repository.ModelRepository;
import kh.virakchantrak.product_crud.service.BrandService;
import kh.virakchantrak.product_crud.service.ModelService;
import kh.virakchantrak.product_crud.spec.BrandFilter;
import kh.virakchantrak.product_crud.spec.BrandSpec;
import kh.virakchantrak.product_crud.spec.ModelFilter;
import kh.virakchantrak.product_crud.spec.ModelSpec;
import kh.virakchantrak.product_crud.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private final BrandService brandService;

    @Override
    public ModelResponseDTO createModel(ModelRequestDTO requestDTO) {

        String brandId = requestDTO.getBrandId();
        BrandResponseDTO brand = brandService.findBrandById(brandId);


        modelRepository.findByNameAndBrandId(requestDTO.getName(), Long.valueOf(brandId))
                .ifPresent(m -> {
                    throw new ApiException(HttpStatus.CONFLICT,
                            "Model: " + requestDTO.getName() + " already exists under brand: " + brand.getName());
                });
        ModelEntity entity = modelMapper.toEntity(requestDTO);
        entity.setBrand(modelMapper.toEntity(brand));
        ModelEntity saved = modelRepository.save(entity);

        return modelMapper.toResponse(saved);
    }

    @Override
    public ModelResponseDTO getModelById(String id) {
        ModelEntity model = modelRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("model", Long.valueOf(id)));

        return modelMapper.toResponse(model);
    }

    @Override
    public ModelResponseDTO updateModel(String id, ModelRequestDTO requestDTO) {
        Long modelId = Long.valueOf(id);
        ModelEntity model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model", modelId));

        String brandId = requestDTO.getBrandId();
        BrandResponseDTO brand = brandService.findBrandById(brandId);

        modelRepository.findByNameAndBrandId(requestDTO.getName(), Long.valueOf(brandId))
                .filter(m -> !m.getId().equals(modelId))
                .ifPresent(m -> {
                    throw new ApiException(HttpStatus.CONFLICT,
                            "Model: " + requestDTO.getName() + " already exists under brand: " + brand.getName());
                });

        model.setName(requestDTO.getName());
        model.setBrand(modelMapper.toEntity(brand));

        ModelEntity updated = modelRepository.save(model);
        return modelMapper.toResponse(updated);
    }

    @Override
    public PaginationResponseDTO<ModelResponseDTO> getModels(Map<String, String> params) {

        // --- 1. Build filter ---
        ModelFilter filter = new ModelFilter();
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
        ModelSpec spec = new ModelSpec(filter);

        // --- 4. Query DB ---
        Page<ModelEntity> page = modelRepository.findAll(spec, pageable);

        // --- 5. Map entities to DTOs ---
        List<ModelResponseDTO> data = modelMapper.toResponse(page.getContent());

        // --- 6. Build and return pagination response ---
        return PaginationResponseDTO.<ModelResponseDTO>builder()
                .data(data)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();
    }
}
