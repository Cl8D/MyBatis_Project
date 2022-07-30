package study.mybatisproject.api.common.mybatis.typehandler;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TypeHandler -> myBatis가 PreparedStatement에 파라미터를 설정하고, ResultSet에서 값을 가져올 때마다
// 적절한 값을 가져오거나, insert 시 적절한 타입을 세팅할 때 사용한다.
// https://mybatis.org/mybatis-3/ko/configuration.html#typeHandlers 참고하기.
public class StringBooleanTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        // boolean 값이 true라면 Y, false라면 N으로 저장되도록.
        // 개인적으로 boolean보다는 enum 타입에서 사용하면 좋을 것 같다. 아니면 애초에 enum으로 설정하든가...?
        ps.setString(i, BooleanUtils.toString(parameter, "Y", "N"));
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        // 마찬가지로 Y, N 값이 들어오면 boolean 형태로 바꿔주도록.
        return BooleanUtils.toBoolean(rs.getString(columnName));
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        return BooleanUtils.toBoolean(rs.getString(columnIndex));
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return BooleanUtils.toBoolean(cs.getString(columnIndex));
    }
}
