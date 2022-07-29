package study.mybatisproject.api.common.framework.paging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 페이징 처리
 */
@Data
public class PageRequest {
    private int page;
    private int size;

    // SQL에서 사용하는 limit, offset 값.
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private int limit;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private int offset;

    public PageRequest(int page, int size, int limit, int offset) {
        this.page = page;
        this.size = size;
        this.limit = limit;
        this.offset = offset;
    }
}
