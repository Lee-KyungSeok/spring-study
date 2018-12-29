package com.kyung.batch.jobs;

import com.kyung.batch.domain.User;
import com.kyung.batch.domain.enums.UserStatus;
import com.kyung.batch.jobs.readers.QueueItemReader;
import com.kyung.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

    private UserRepository userRepository;

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep) { // 빌더 객체 및 Step 을 주입

        return jobBuilderFactory.get("inactiveUserJob")
                .preventRestart() // Job 의 재실행을 막는다.
                .start(inactiveJobStep)  // 특정 Step 을 제일 먼저 실행시킨다.
                .build();
    }

    @Bean
    public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("inactiveUserStep")
                .<User, User> chunk(10) // 커밋 단위는 10개, 입력타입과 출력타입은 User
                .reader(inactiveUserReader())
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .build();
    }

    @Bean
    @StepScope // singleton 이 아닌 Step 의 주기에 따라 새로운 빈을 생성 (단, 구현된 반환타입을 명시해 반환해야만 한다.)
    public QueueItemReader<User> inactiveUserReader() {
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);

        return new QueueItemReader<>(oldUsers);
    }

    private ItemProcessor<User, User> inactiveUserProcessor() {
        return User::setInactive;
    }

    private ItemWriter<? super User> inactiveUserWriter() {
        // 청크 단위에 따라 리스트의 갯수를 다르게 받는다(여기서는 10개) 그리고 이를 DB 에 한번에 저장한다.
        return ((List<? extends User> users) -> userRepository.saveAll(users));
    }

}
