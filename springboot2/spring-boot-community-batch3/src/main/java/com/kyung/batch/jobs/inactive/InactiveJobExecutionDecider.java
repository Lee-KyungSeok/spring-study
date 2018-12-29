package com.kyung.batch.jobs.inactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Random;

// 세부적인 조건에 따라 flow 를 다르게 줄 수 있다.
@Slf4j
public class InactiveJobExecutionDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if(new Random().nextInt() > 0 ) {
            log.info("FlowExecutionStatus.Complete");
            return FlowExecutionStatus.COMPLETED;
        }
        log.info("lowExecutionStatus.FAILED");
        return FlowExecutionStatus.FAILED;
    }
}
