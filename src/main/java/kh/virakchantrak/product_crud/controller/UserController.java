package kh.virakchantrak.product_crud.controller;

import jakarta.validation.Valid;
import kh.virakchantrak.product_crud.dto.request.CreateUserRequestDTO;
import kh.virakchantrak.product_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
//    private final UserService userService;
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping
//    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO requestDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDTO));
//    }
}
