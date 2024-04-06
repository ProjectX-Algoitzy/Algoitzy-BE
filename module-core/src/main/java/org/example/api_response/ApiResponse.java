package org.example.api_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.api_response.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder
@Builder
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(Include.NON_NULL)
    private T result;


    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), result);
    }
    public static <T> ApiResponse<T> onSuccess() {
        return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), null);
    }
    public static <T> ApiResponse<T> onCreate(T result) {
        return new ApiResponse<>(true, SuccessStatus.CREATED.getCode(), SuccessStatus.CREATED.getMessage(), result);
    }
    public static <T> ApiResponse<T> onCreate() {
        return new ApiResponse<>(true, SuccessStatus.CREATED.getCode(), SuccessStatus.CREATED.getMessage(), null);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
