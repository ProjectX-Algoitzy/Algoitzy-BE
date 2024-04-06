package org.example.api_response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
    OK(HttpStatus.OK, "COMMON200", "Your request has been successfully performed."),
    CREATED(HttpStatus.CREATED, "COMMON201", "Resource creation successfully performed.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
