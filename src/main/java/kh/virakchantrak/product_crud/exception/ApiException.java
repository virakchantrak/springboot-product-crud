package kh.virakchantrak.product_crud.exception;

import kh.virakchantrak.product_crud.common.conts.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    // ErrorCode-based
    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.name();
    }

    // ErrorCode + custom message
    public ApiException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.name();
    }

    // Ad-hoc HTTP status + message
    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = null;
    }
}
