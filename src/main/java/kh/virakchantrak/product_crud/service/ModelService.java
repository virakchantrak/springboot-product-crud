package kh.virakchantrak.product_crud.service;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.dto.response.ModelResponseDTO;
import kh.virakchantrak.product_crud.dto.response.PaginationResponseDTO;

import java.util.Map;

public interface ModelService {
    ModelResponseDTO createModel(ModelRequestDTO requestDTO);
    ModelResponseDTO getModelById(String id);
    ModelResponseDTO updateModel(String id, ModelRequestDTO requestDTO);
    PaginationResponseDTO<?> getModels(Map<String, String> params);
}
