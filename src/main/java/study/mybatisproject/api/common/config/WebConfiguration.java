package study.mybatisproject.api.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import study.mybatisproject.api.common.config.servlet.handler.BaseHandlerInterceptor;
import study.mybatisproject.api.common.framework.paging.PageRequestArgumentResolver;
import study.mybatisproject.domain.board.entity.BaseCodeLabelEnum;

import java.util.List;
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

    @Bean
    public GlobalConfig config() {
        return new GlobalConfig();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addInterceptor를 통해 인터셉터를 등록할 수 있다.
        registry.addInterceptor(baseHandlerInterceptor());
        // 이 옵션 외에도 url에 대한 패턴 매칭도 가능! (addPathPatterns, excludePathPatterns...)
    }

    // 구현한 pageResolver 추가해주기.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageRequestArgumentResolver());
    }

    // ObjectMapper 등록
    @Bean
    public ObjectMapper objectMapper() {
        // objectMapper -> JSON 형식 사용 시 응답을 직렬화하고, 요청을 역직렬화할 때 사용.
        // 직렬화: Object -> String, 역직렬화: String -> Object.
        ObjectMapper objectMapper = new ObjectMapper();

        // 직렬화할 때 사용한다. 사용자가 직접 직렬화 커스텀할 때 사용하는 것 같다.
        // 정확하게는 Jackson에서 제공하는 module을 만드는 클래스라고 생각하면 좋을 것 같다!
        SimpleModule simpleModule = new SimpleModule();

        // BaseCaseLabelEnum 클래스를, BaseCodeLabelEnumJsonSerializer를 통해 직렬화를 진행하겠다는 것.
        simpleModule.addSerializer(BaseCodeLabelEnum.class, new BaseCodeLabelEnumJsonSerializer());
        // 그리고 해당 모듈을 등록해주기!
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // objectMapper를 우리가 만든 objectMapper로 사용하는 것.
        jsonView.setObjectMapper(objectMapper());
        return jsonView;
    }
}
