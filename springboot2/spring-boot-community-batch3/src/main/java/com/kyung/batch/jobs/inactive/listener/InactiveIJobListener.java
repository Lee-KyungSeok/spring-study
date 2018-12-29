package com.kyung.batch.jobs.inactive.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


// JobExecutionListener 이외에도 ChunkListener, ItemReaderListener, StepListener 등이 존재한다.
@Slf4j
@Component
public class InactiveIJobListener implements JobExecutionListener {

    // 잡 실행 전 수행
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Before Job");
    }

    // 잡 실행 후 수행
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("After Job");
    }
}
