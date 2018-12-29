package com.kyung.batch.jobs.inactive;

import com.kyung.batch.domain.User;
import com.kyung.batch.domain.enums.UserStatus;
import com.kyung.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// Tasklet 은 임의의 Step 을 실행할 때 하나의 작업으로 처리하는 방식

@Component
@AllArgsConstructor
public class InactiveItemTasklet implements Tasklet {

    private UserRepository userRepository;

    // 읽기 -> 처리 -> 쓰기를 한군데서 한번에 처리한다.
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // reader
        Date nowDate = (Date) chunkContext
                .getStepContext()
                .getJobParameters()
                .get("nowDate");

        LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());
        List<User> inactiveUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(now.minusYears(1), UserStatus.ACTIVE);

        // processor
        inactiveUsers = inactiveUsers.stream()
                .map(User::setInactive)
                .collect(Collectors.toList());

        // writer
        userRepository.saveAll(inactiveUsers);

        return RepeatStatus.FINISHED;
    }
}
