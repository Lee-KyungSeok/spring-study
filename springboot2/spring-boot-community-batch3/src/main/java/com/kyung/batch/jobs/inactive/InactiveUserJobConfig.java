package com.kyung.batch.jobs.inactive;

import com.kyung.batch.domain.User;
import com.kyung.batch.domain.enums.UserStatus;
import com.kyung.batch.jobs.inactive.listener.InactiveIJobListener;
import com.kyung.batch.jobs.inactive.listener.InactiveStepListener;
import com.kyung.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

    private final static int CHUNK_SIZE = 15;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job inactiveUserJob(
            JobBuilderFactory jobBuilderFactory,
            InactiveIJobListener inactiveIJobListener,
            Step partitionerStep
            ) {

        return jobBuilderFactory.get("inactiveUserJob")
                .preventRestart() // Job 의 재실행을 막는다.
                .listener(inactiveIJobListener) // 리스너 세팅
                .start(partitionerStep)  // 특정 Step 을 제일 먼저 실행시킨다.
                .build();
    }

    @Bean
    @JobScope
    public Step partitionerStep(
            StepBuilderFactory stepBuilderFactory,
            Step inactiveJobStep) {

        return stepBuilderFactory.get("partitionerStep")
                .partitioner("partitionerStep", new InactiveUserPartitioner()) // InactiveUserPartitioner 을 등록한다.
                .gridSize(5) // 파라미터로 사용한 gridSize 를 넣어준다.(여기서는 값이 3이므로 이보다 큰 5를 넣음)
                .step(inactiveJobStep)
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public Step inactiveJobStep(
            StepBuilderFactory stepBuilderFactory,
            ListItemReader<User> inactiveUserReader,
            InactiveStepListener inactiveStepListener,
            TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("inactiveUserStep")
                .<User, User> chunk(10) // 커밋 단위는 10개, 입력타입과 출력타입은 User
                .reader(inactiveUserReader)
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .listener(inactiveStepListener) // Step 관련 리스너 세팅
                .taskExecutor(taskExecutor) // 빈으로 설정한 taskExecutor 설정
                .throttleLimit(2) // 제한 횟수만큼만 스레드를 동시에 실행시키겠다는 뜻 (시스템에 할당된 스레드 풀의 크기보다 작은 값으로 설정되어야 한다)
                .build();
    }

    // 실행될때마다 쓰레드를 만들어 처리한다. (병렬처리)
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("Batch_task");
    }

    @Bean
    @StepScope // singleton 이 아닌 Step 의 주기에 따라 새로운 빈을 생성 (단, 구현된 반환타입을 명시해 반환해야만 한다.)
    public ListItemReader<User> inactiveUserReader(@Value("#{jobParameters[nowDate]}")Date nowDate, UserRepository userRepository) { // Valid 를 이용해서 jobParameters 에서 곧바로 주입받을 수 있다.

        // 전달받은 현재 날짜값을 LocalDateTime 으로 전환한다.
        LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());

        // ListItemReader 을 이용하면 모든 데이터를 한번에 가져와 메모리에 올리고 read() 메서드로 하나씩 배치 처리를 하게 된다.
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(now.minusYears(1), UserStatus.ACTIVE);

        return new ListItemReader<>(oldUsers);
    }

    public ItemProcessor<User, User> inactiveUserProcessor() {
        return User::setInactive;
    }

    // entityManagerFactory 만 설정해주면 Processor 에서 넘어온 데이터를 청크 단위로 저장한다.
    private JpaItemWriter<User> inactiveUserWriter() {
        JpaItemWriter<User> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
