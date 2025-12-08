package kh.virakchantrak.product_crud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

}
