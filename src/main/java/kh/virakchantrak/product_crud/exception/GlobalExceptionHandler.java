package kh.virakchantrak.product_crud.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException apiException) {
        ErrorResponse errorResponse = new ErrorResponse(apiException.getHttpStatus(), apiException.getMessage());
        return ResponseEntity.status(apiException.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<?> badCred() {
        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
    }
}
