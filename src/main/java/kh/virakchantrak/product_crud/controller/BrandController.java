package kh.virakchantrak.product_crud.controller;

import kh.virakchantrak.product_crud.dto.request.BrandRequestDTO;
import kh.virakchantrak.product_crud.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody BrandRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.createBrand(requestDTO));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBrandById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandService.findBrandById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBrandById(@PathVariable String id, @RequestBody BrandRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.updateBrandById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBrandById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.deleteBrandById(id));
    }

    @GetMapping
    private ResponseEntity<?> getBrands(@RequestParam Map<String, String> params) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getBrands(params));
    }
}
