package kh.virakchantrak.product_crud.service;

import kh.virakchantrak.product_crud.common.pagination.PaginationResponseDTO;
import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;

import java.util.Map;

public interface BrandService {

    BrandResponseDTO createBrand(BrandRequestDTO requestDTO);

    BrandResponseDTO findBrandById(String id);

    BrandResponseDTO updateBrandById(String id, BrandRequestDTO requestDTO);

    String deleteBrandById(String id);

    PaginationResponseDTO<?> getBrands(Map<String, String> params);
}
