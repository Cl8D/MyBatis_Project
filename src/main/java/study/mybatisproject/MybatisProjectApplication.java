package study.mybatisproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling // 스케줄러 추가
public class MybatisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisProjectApplication.class, args);
	}

}
