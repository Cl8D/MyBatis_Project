package study.mybatisproject.api.common.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Slf4j
public class ExampleScheduler {
    // scheduler를 사용하면 일정한 시간 간격, 혹은 특정 일정에 코드가 실행되도록 할 수 있다.
    // method는 void 형으로, 파라미터는 없는 형태여야 한다.

    // 5초에 한 번씩 실행되도록.
    // cron 표현식 => 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7) 순서.
//    @Scheduled(cron = "*/5 * * * * *")

    // 서버마다 다르게 적용하기 위해서 bean으로 등록된 이름 자체를 cron으로 등록한다.
    @Scheduled(cron = "#{@schedulerCronExample1}")
    public void schedule1() {
        log.info("schedule1 동작 중 : {}", Calendar.getInstance().getTime());
    }
}
