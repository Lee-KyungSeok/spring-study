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
            Flow inactiveJobFlow
//            Flow multiFlow,
            ) { // 빌더 객체 및 Step 을 주입

        return jobBuilderFactory.get("inactiveUserJob")
                .preventRestart() // Job 의 재실행을 막는다.
                .listener(inactiveIJobListener) // 리스너 세팅
//                .start(multiFlow) // 멀티 플로우는 여기서는 사실 적절하지 않다.
                .start(inactiveJobFlow)  // 특정 Step 을 제일 먼저 실행시킨다.
                .end()
                .build();
    }

    @Bean
    public Flow multiFlow(Step inactiveJobStep) {
        // flows 를 5개 생성해서 저장
        Flow flows[] = new Flow[5];
        IntStream.range(0, flows.length).forEach(i ->
                flows[i] = new FlowBuilder<Flow>("MultiFlow"+i).from(inactiveJobFlow(inactiveJobStep)).end());

        // 빌더를 가져옴
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("MultiFlow");

        return flowBuilder
                .split(taskExecutor()) // taskExecuter 지정
                .add(flows) // 할당된 5개의 flows 배열을 추가
                .build();
    }

    @Bean // multiflow 사용시에는 싱글턴을 제거해야 하므로 Bean 을 삭제한다.
    public Flow inactiveJobFlow(Step inactiveJobStep) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("inactiveJobFlow");

        return flowBuilder
                .start(new InactiveJobExecutionDecider()) // 조건을 시작할때 걸었다.
                .on(FlowExecutionStatus.FAILED.getName()).end() // Failed 반환시 end 로 끝낸다.
                .on(FlowExecutionStatus.COMPLETED.getName()).to(inactiveJobStep) // Complete 반환 시 inactiveJobStep 을 실행한다.
                .end();
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


    @Bean
    @StepScope // singleton 이 아닌 Step 의 주기에 따라 새로운 빈을 생성 (단, 구현된 반환타입을 명시해 반환해야만 한다.)
    public ListItemReader<User> inactiveUserReader(@Value("#{jobParameters[nowDate]}")Date nowDate, UserRepository userRepository) { // Valid 를 이용해서 jobParameters 에서 곧바로 주입받을 수 있다.

        // 전달받은 현재 날짜값을 LocalDateTime 으로 전환한다.
        LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());

        // ListItemReader 을 이용하면 모든 데이터를 한번에 가져와 메모리에 올리고 read() 메서드로 하나씩 배치 처리를 하게 된다.
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(now.minusYears(1), UserStatus.ACTIVE);

        return new ListItemReader<>(oldUsers);
    }

    /*
    // 만약 많은 데이터(수백만개?)를 가져와야 하는 경우 ListItemReader는 문제가 될 수 있으므로 원하는 갯수만큼 가져오는 JpaPagingItemReader 를 사용할 수 있다.
    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<User> inactiveUserJpaReader() {

        JpaPagingItemReader<User> jpaPagingItemReader = new JpaPagingItemReader() {

            // 조회용 인덱스 값을 0으로 반환하여 청크를 건너뛰지 않도록 설정한다.
            @Override
            public int getPage() {
                return 0;
            }
        };

        // JpaPagingItemReader 를 사용하려면 쿼리를 직접 짜야 한다.
        jpaPagingItemReader.setQueryString("select u from User ad u where u.updatedDate < :updatedDate and u.status = :status");

        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("updatedDate", now.minusYears(1));
        map.put("status", UserStatus.ACTIVE);

        // 사용할 파라미터 설정
        jpaPagingItemReader.setParameterValues(map);
        // 트랜잭션을 관리해줄 entityManagerFactory 설정
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        // 한번에 읽어올 크기 설정
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);

        return jpaPagingItemReader;
    }
    */

    // 실행될때마다 쓰레드를 만들어 처리한다. (병렬처리)
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("Batch_task");
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
