package com.kyung.batch.jobs.inactive;

import com.kyung.batch.domain.enums.Grade;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class InactiveUserPartitioner implements Partitioner {

    private static final String GRADE = "grade";
    private static final String INACTIVE_USER_TASK = "inactiveUserTask";

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) { // 최대 분할 수를 지정

        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        Grade[] grades = Grade.values(); // enum 의 모든 값을 가져온다.
        for (int i=0, length=grades.length; i<length; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putString(GRADE, grades[i].name()); // Step 에서 파라미터로 Grade 값을 받아 사용한다. 이를 context 애 추가한다.
            map.put(INACTIVE_USER_TASK + i, context); // 반환되는 map 에 파티션 키값과, 설정한 context 를 넣어준다.
        }
        return map;
    }
}
