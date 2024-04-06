package org.example.api_response.exception;


import org.example.api_response.dto.ApiDto;

public interface BaseErrorCode {

    ApiDto getReasonHttpStatus();
}
