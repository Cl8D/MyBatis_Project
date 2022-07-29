package study.mybatisproject.api.common.framework.paging;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import study.mybatisproject.api.common.framework.paging.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 페이징 쿼리 처리. limit, offset 값을 자동 계산하여
 * pageRequest 클래스에 담아 컨트롤러에서 받을 수 있도록.
 */
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_PARAMETER_PAGE = "page";
    private static final String DEFAULT_PARAMETER_SIZE = "size";
    private static final int DEFAULT_SIZE = 10;

    // 어떤 파라미터에 대해서 작업을 수행할 것인지.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 들어온 파라미터가 PageRequest를 상속/구현했는지.
        // 즉, 파라미터가 PageRequest 타입이라면 처리한다는 뜻.
        return PageRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    // 파라미터에 대한 실질적인 로직을 처리. - 페이징 처리를 하자.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 클라이언트로부터 요청이 들어왔을 때, 서버는 httpServletRequest를 생성하는데,
        // getParameter를 통해서 원하는 데이터를 꺼내올 수 있다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 페이지 꺼내오기, 숫자 형태로 형변환 해주기. 기본값은 1.
        int page = NumberUtils.toInt(request.getParameter(DEFAULT_PARAMETER_PAGE), 1);
        // 개수. 기본값은 10.
        int offset = NumberUtils.toInt(request.getParameter(DEFAULT_PARAMETER_SIZE), DEFAULT_SIZE);
        // 시작 지점
        int limit = (offset * page) - offset;

        logger.info("page : {}", page);
        logger.info("limit : {}, offset: {}", limit, offset);

        return new PageRequest(page, offset, limit, offset);
    }
}
