package study.mybatisproject.api.controller.dto;

import lombok.Data;
import study.mybatisproject.api.common.config.http.ApiResponseCode;

@Data
public class ApiResponse<T> {
    private ApiResponseCode code;
    private String message;
    private T data;

    // 응답이 성공이라면 해당 response 호출
    public ApiResponse(T data) {
        this.code = ApiResponseCode.SUCCESS;
        this.data = data;
    }

    // 실패라면 해당 response 호출
    public ApiResponse(ApiResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
