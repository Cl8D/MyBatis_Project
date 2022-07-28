package study.mybatisproject.api.common.config.exception;

import study.mybatisproject.api.common.config.http.ApiResponseCode;

// 공통 에러포맷을 구현하기 위해서 abstract로.
public abstract class AbstractBaseException extends RuntimeException {
    private static final long serialVersionUID = 8342235231880246631L;

    protected ApiResponseCode responseCode;
    protected Object[] args;

    public AbstractBaseException() {
    }

    public AbstractBaseException(ApiResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ApiResponseCode getResponseCode() {
        return responseCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
