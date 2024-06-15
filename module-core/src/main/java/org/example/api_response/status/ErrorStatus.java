package org.example.api_response.status;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.api_response.dto.ApiDto;
import org.example.api_response.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON4001"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4002"),
    KEY_NOT_EXIST(HttpStatus.BAD_REQUEST, "COMMON4003"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000"),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized401"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "40000");
    private final HttpStatus httpStatus;
    private final String code;
    private String message;


    public String getMessage(String message) {
        this.message = message;
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public ApiDto getReasonHttpStatus() {
        return ApiDto.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}
