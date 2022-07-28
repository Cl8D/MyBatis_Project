package study.mybatisproject.api.common.config.servlet.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 참고로, springBoot에는 logback이 기본 설정에 포함되어 있다.
// HandlerInterceptor를 통해 특정한 URI 호출을 가로챌 수 있으며, 기존 컨트롤러 로직을 수정하지 않아도 사전, 사후 제어가 가능하다.
// 참고하면 좋음: https://elfinlas.github.io/2017/12/28/SpringBootInterceptor/
public class BaseHandlerInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(getClass());

    // 지정된 컨트롤러의 동작 이전에 수행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle requestURI : {}", request.getRequestURI());
        return true;
    }

    // 컨트롤러의 동작 이후에 처리, 디스패처 서블릿이 화면을 처리하기 전에 동작한다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle requestURI : {}", request.getRequestURI());
    }
}
