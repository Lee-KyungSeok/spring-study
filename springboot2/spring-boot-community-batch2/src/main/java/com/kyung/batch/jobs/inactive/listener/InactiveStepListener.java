package com.kyung.batch.jobs.inactive.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InactiveStepListener {

    // 인터페이스 대신 어노테이션을 활용하여 리스너를 달 수도 있다.
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("Before Step");
    }

    @AfterStep
    public void after(StepExecution stepExecution) {
        log.info("After Step");
    }
}
