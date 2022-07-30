package study.mybatisproject.api.common.config.web.bind.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestConfig {
    // 로그인 체크 메서드. 디폴트는 false
    // 나중에 로그인이 필요한 Controller에 @GetMapping / @PostMapping 하는 곳에
    // @RequestConfig 추가해주고, loginCheck=true 설정해주기
    boolean loginCheck() default false;
}
