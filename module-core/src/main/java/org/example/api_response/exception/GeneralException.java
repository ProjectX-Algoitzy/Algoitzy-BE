package org.example.api_response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.api_response.dto.ApiDto;
import org.example.api_response.status.ErrorStatus;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public GeneralException(ErrorStatus errorStatus, String message) {
        super(errorStatus.getMessage(message));
        this.code = errorStatus;
    }

    public ApiDto getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
