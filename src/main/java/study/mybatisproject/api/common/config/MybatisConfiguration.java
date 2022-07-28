package study.mybatisproject.api.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Mybatis 설정
 * @author jiwon
 */
@Configuration
@MapperScan(basePackages = "study.mybatisproject.domain.board.repository")
public class MybatisConfiguration {
    @Bean
    // Mybatis와 spring을 연결할 때 SqlSessionFactoryBean을 사용한다.
    // 빈으로 등록하였기 때문에, 애플리케이션 시작 시점에 SqlSessionFactory를 빌드하고, sqlSessionFactory라는 이름으로 저장한다.
    // 참고로, factoryBean의 .getObject() 결과가 sqlSessionFactory이다.
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource,
                                               ApplicationContext applicationContext) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // XML 매퍼 설정 - 쿼리에 대한 정보가 xml에 담겨있다!
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/sql/*.xml"));
        SqlSessionFactory factory = factoryBean.getObject();
        // underScore(_)를 camelCase로 매칭해준다.
        // ex) board_id = boardId
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return factoryBean.getObject();
    }

    // SqlSessionTemplate은 SqlSession을 구현하고, 코드에서 대체한다.
    // thread-safe 하며, 여러 개의 DAO/mapper에서 공유가 가능하다.
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
        // 생성자의 인지로 sqlSessionFactor를 주입한다.
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
