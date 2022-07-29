package study.mybatisproject.api.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import study.mybatisproject.domain.board.entity.BaseCodeLabelEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON 변환 시 BaseCodeLabelEnum 클래스에 대한 변환을 동일하게 처리해준다.
 */
// 객체나 값을 json으로 직렬화하는 기능 제공.
public class BaseCodeLabelEnumJsonSerializer extends JsonSerializer<BaseCodeLabelEnum> {
    @Override
    public void serialize(BaseCodeLabelEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // map - json 변환.
        // https://engaspect.tistory.com/27 이런 식으로 필요한 key를 넣어서 주면 좋을 것 같다.
        Map<String, Object> map = new HashMap<>();
        map.put("code", value.code());
        map.put("label", value.label());
        gen.writeObject(map);
    }
}
