package com.kyung.batch;

import com.kyung.batch.domain.enums.UserStatus;
import com.kyung.batch.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // h2 DB 를 사용한다고 명시
public class InactiveUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_inactive_change() throws Exception {

        // Date 는 JobParameter 에서 허용되는 파라미터중 하나이다
        Date nowDate = new Date();

        // JobParametersBuilder 를 이용해서 toJobParameters 를 만들 수 있다.
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder()
                        .addDate("nowDate", nowDate)
                        .toJobParameters()
        );

        // getStatus 값이 COMPLETED 인지 확인
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // 업데이트된 날짜가 1년 전이고, User 상태값이 ACTIVE 인 사용자들이 없어야 함
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

    }

}
