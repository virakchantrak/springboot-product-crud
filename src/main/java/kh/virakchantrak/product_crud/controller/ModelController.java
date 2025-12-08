package kh.virakchantrak.product_crud.controller;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody ModelRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modelService.createModel(requestDTO));
    }
}
