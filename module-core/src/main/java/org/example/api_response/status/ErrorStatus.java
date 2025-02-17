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

    // NOTICE : 프론트 alert 창에 에러 메시지
    NOTICE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "NOTICE"),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE"),
    NOTICE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "NOTICE"),
    NOTICE_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NOTICE"),

    // 404_PAGE : 프론트 404 Page
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "404_PAGE"),
    // 401_PAGE : 프론트 401 Page
    PAGE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401_PAGE"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON4001"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4002"),
    KEY_NOT_EXIST(HttpStatus.NOT_FOUND, "COMMON4003"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000"),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized401"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED");
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
