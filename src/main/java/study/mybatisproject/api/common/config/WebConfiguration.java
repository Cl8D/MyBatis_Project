package study.mybatisproject.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.mybatisproject.api.common.config.servlet.handler.BaseHandlerInterceptor;

import java.util.Locale;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    // 국제화와 관련된 기능.
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:/messages/message");
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(60);
        source.setDefaultLocale(Locale.KOREA);
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    // 빈으로 등록해주기
    @Bean
    public BaseHandlerInterceptor baseHandlerInterceptor() {
        return new BaseHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addInterceptor를 통해 인터셉터를 등록할 수 있다.
        registry.addInterceptor(baseHandlerInterceptor());
        // 이 옵션 외에도 url에 대한 패턴 매칭도 가능! (addPathPatterns, excludePathPatterns...)
    }
}
