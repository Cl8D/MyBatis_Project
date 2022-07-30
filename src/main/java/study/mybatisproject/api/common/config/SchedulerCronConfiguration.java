package study.mybatisproject.api.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerCronConfiguration {
    @Autowired
    private GlobalConfig config;

    @Bean
    public String schedulerCronExample1() {
        // 프로퍼티의 값이 bean으로 등록된다.
        return config.getSchedulerCronExample1();
    }

}
