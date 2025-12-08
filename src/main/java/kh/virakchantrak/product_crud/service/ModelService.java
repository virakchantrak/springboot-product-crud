package kh.virakchantrak.product_crud.service;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.dto.response.ModelResponseDTO;

public interface ModelService {
    ModelResponseDTO createModel(ModelRequestDTO requestDTO);
}
