package study.mybatisproject.api.common.config.exception;

import study.mybatisproject.api.common.config.http.ApiResponseCode;

public class ApiException extends AbstractBaseException {
    private static final long serialVersionUID = 8342235231880246631L;

    public ApiException() {
    }

    public ApiException(ApiResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ApiException(ApiResponseCode responseCode, String[] args) {
        this.responseCode = responseCode;
        this.args = args;
    }
}
