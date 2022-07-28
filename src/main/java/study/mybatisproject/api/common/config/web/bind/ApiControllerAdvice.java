package study.mybatisproject.api.common.config.web.bind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import study.mybatisproject.api.common.config.exception.ApiException;
import study.mybatisproject.api.controller.dto.ApiResponse;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ApiControllerAdvice {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {ApiException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private ApiResponse<?> handleApiException (ApiException e, WebRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8"); // 한글 깨짐 방지
        // code, message
        return new ApiResponse<>(e.getResponseCode(),
                messageSource.getMessage(e.getResponseCode().name(), e.getArgs(), null));
    }
}
