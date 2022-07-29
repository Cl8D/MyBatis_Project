package study.mybatisproject.api.common.framework.paging.domain;

import lombok.Data;

/**
 * 페이지 요청 정보 및 파라미터 정보
 */
@Data
public class PageRequestParam<T> {
    private PageRequest pageRequest;
    // 원하는 조건을 넣을 수 있도록 제네릭으로 처리!
    private T parameter;

    public PageRequestParam(PageRequest pageRequest, T parameter) {
        this.pageRequest = pageRequest;
        this.parameter = parameter;
    }
}
