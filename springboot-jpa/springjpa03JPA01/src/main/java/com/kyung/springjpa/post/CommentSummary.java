package com.kyung.springjpa.post;

import org.springframework.beans.factory.annotation.Value;

// 클래스로도 구현이 가능하다 (getter 를 넣어주면 된다. 인터페이스면 아래처럼 사용)
public interface CommentSummary {

    String getComment();

    int getUp();

    int getDown();

    // 이를 추가하면 원하는 방식으로 가져올 수 있다 (open projection)
    // 단, target 때문에 모든 칼럼을 가져오게 된다.
//    @Value("#{target.up + ' ' + target.down}")
//    String getVotes();

    // 아래처럼 하면 성능상 이점도 얻을 수 있다.
    default String getVotes() {
        return getUp() + " " + getDown();
    }

}
