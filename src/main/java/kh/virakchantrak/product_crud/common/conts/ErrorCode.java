package kh.virakchantrak.product_crud.common.conts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Conflict
    C001(HttpStatus.CONFLICT, "Name already exist");

    private final HttpStatus httpStatus;
    private final String message;
}
