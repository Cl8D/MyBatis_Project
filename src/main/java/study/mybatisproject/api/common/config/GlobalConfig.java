package study.mybatisproject.api.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class GlobalConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    private String uploadFilePath;
    private String schedulerCronExample1;

    private boolean local;
    private boolean dev;
    private boolean prod;

    @PostConstruct
    public void init() {
      log.info("init");
      String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        String activeProfile = "local";
        if(activeProfiles.length != 0) {
            activeProfile = activeProfiles[0];
        }
        // 로컬 환경에서의 프로퍼티 파일. 실제로는 globals-dev, globals-prod 등으로 만들기
        String resourcePath = String.format("classpath:globals/global-%s.properties", activeProfile);

        try {
            Resource resource = resourceLoader.getResource(resourcePath);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            // 프로퍼티 값을 받아오기
            this.uploadFilePath = properties.getProperty("uploadFile.path");

            // 개발, 운영 서버마다 프로퍼티를 다르게 해서 스케줄러 설정을 다르게 하기 위해서.
            this.schedulerCronExample1 = properties.getProperty("scheduler.cron.example1");
            this.local = activeProfile.equals("local");
            this.dev = activeProfile.equals("dev");
            this.prod = activeProfile.equals("prod");

        } catch (IOException e) {
            log.error("exception", e);
        }
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public String getSchedulerCronExample1() {
        return schedulerCronExample1;
    }

    public boolean isLocal() {
        return local;
    }


}
