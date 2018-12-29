package com.kyung.batch;

import com.kyung.batch.domain.enums.UserStatus;
import com.kyung.batch.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InactiveUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_inactive_change() throws Exception {

        // job 을 실행 실행 결과에 대한 정보를 담은 JobExecution 이 리턴
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // getStatus 값이 COMPLETED 인지 확인
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // 업데이트된 날짜가 1년 전이고, User 상태값이 ACTIVE 인 사용자들이 없어야 함
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

    }

}
