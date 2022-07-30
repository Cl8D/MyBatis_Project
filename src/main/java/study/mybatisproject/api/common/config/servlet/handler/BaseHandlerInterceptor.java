package study.mybatisproject.api.common.config.servlet.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import study.mybatisproject.api.common.config.exception.ApiException;
import study.mybatisproject.api.common.config.http.ApiResponseCode;
import study.mybatisproject.api.common.config.web.bind.annotation.RequestConfig;

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

        // 컨트롤러 메서드 호출 전에 로그인 체크 조건 추가하기.
        // @RequestMapping과 하위 어노테이션(@GetMapping, @PostMapping 등)이 붙은 메서드 정보 추상화한 객체
        if(handler instanceof HandlerMethod) {
            // 요청 URL이 컨트롤러 메서드에 매핑이 되어 있으면, HandlerMethod로 캐스팅 가능
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            logger.info("handlerMethod : {}", handlerMethod);
            // 현재 URL에 매핑된 컨트롤러 메서드에 선언된 어노테이션 가져오기
            RequestConfig requestConfig = handlerMethod.getMethodAnnotation(RequestConfig.class);

            // save()에만 @requestConfig를 추가해서, 선언 안 된 곳은 null 발생하니까 조건 추가해주기.
            if (requestConfig != null) {
                // 로그인 체크가 필수인 경우 - 아직 세션, 토큰 정보가 없으니까 그냥 바로 에러 메시지 넘기기
                if (requestConfig.loginCheck()) {
                    throw new ApiException(ApiResponseCode.LOGIN_REQUIRED,
                            new String[] {request.getRequestURI()});
                }
            }

        }
        return true;
    }

    // 컨트롤러의 동작 이후에 처리, 디스패처 서블릿이 화면을 처리하기 전에 동작한다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle requestURI : {}", request.getRequestURI());
    }
}
