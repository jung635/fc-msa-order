package dev.practice.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Result result;
    private T data; //데이터 형태
    private String message;
    private String errorCode;

    public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>)  CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> CommonResponse<T> fail(T data, String errorCode) {
        return (CommonResponse<T>)  CommonResponse.builder()
                .result(Result.FAIL)
                .data(data)
                .errorCode(errorCode)
                .build();
    }

    public static <T> CommonResponse<T> fail(ErrorCode errorCode) {
        return (CommonResponse<T>)  CommonResponse.builder()
                .result(Result.FAIL)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .build();
    }

    public enum Result{
        SUCCESS, FAIL
    }
}
