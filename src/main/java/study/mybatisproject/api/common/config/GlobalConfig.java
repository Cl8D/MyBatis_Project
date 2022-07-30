package study.mybatisproject.api.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class GlobalConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    private String uploadFilePath;
    private String uploadResourcePath;
    private String schedulerCronExample1;

    private boolean local;

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

            Path relativePath = Paths.get(""); // 현재 프로젝트의 상대 경로
            // 프로퍼티 값을 받아오기 - 절대 경로 + 프로퍼티에서 받아온 이름으로
            this.uploadFilePath = relativePath.toAbsolutePath() + properties.getProperty("uploadFile.path");
            // static resource의 경로 - 브라우저에서 접속할 수 있는 경로
            this.uploadResourcePath = properties.getProperty("uploadFile.resourcePath");

            log.info("uploadPath = {}" , uploadFilePath);

            // 개발, 운영 서버마다 프로퍼티를 다르게 해서 스케줄러 설정을 다르게 하기 위해서.
            this.schedulerCronExample1 = properties.getProperty("scheduler.cron.example1");
            this.local = activeProfile.equals("local");

        } catch (IOException e) {
            log.error("exception", e);
        }
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public String getUploadResourcePath() { return uploadResourcePath; }
    public String getSchedulerCronExample1() {
        return schedulerCronExample1;
    }

    public boolean isLocal() {
        return local;
    }


}
