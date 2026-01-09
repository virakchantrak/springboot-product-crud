package kh.virakchantrak.product_crud.service.impl;

import kh.virakchantrak.product_crud.common.pagination.PaginationResponseDTO;
import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.dto.response.ModelResponseDTO;
import kh.virakchantrak.product_crud.entity.ModelEntity;
import kh.virakchantrak.product_crud.exception.ApiException;
import kh.virakchantrak.product_crud.exception.ResourceNotFoundException;
import kh.virakchantrak.product_crud.mapper.ModelMapper;
import kh.virakchantrak.product_crud.repository.BrandRepository;
import kh.virakchantrak.product_crud.repository.ModelRepository;
import kh.virakchantrak.product_crud.service.BrandService;
import kh.virakchantrak.product_crud.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

       return null;
    }
}
