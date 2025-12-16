package kh.virakchantrak.product_crud.controller;

import kh.virakchantrak.product_crud.dto.request.ModelRequestDTO;
import kh.virakchantrak.product_crud.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    public final ModelService modelService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModelRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modelService.createModel(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModelById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(modelService.getModelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateModelById(@PathVariable String id, @RequestBody ModelRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(modelService.updateModel(id, requestDTO));
    }

    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam Map<String, String> params) {
        return ResponseEntity.status(HttpStatus.OK).body(modelService.getModels(params));
    }
}
