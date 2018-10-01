package com.kyung.springjpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface CommentRepository extends MyRepository<Comment, Long> {

    Page<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount, Pageable pageable); // ignoreCase 하면 uppercase 로 변환해서 찾게 된다.

    Stream<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword); // ignoreCase 하면 uppercase 로 변환해서 찾게 된다.

    @Async // 별도의 쓰레드에 위임하는 것 // Future 보다는 ListenableFuture 를 쓰는 것을 추천(테스트 코드 짜기가 간편)=> 하지만 이도 권장하고 싶지는 않다.(EnableAsync 붙이는 순간 매우 복잡해진다.(Thread 의 문제)
    ListenableFuture<List<Comment>> findByCommentContains(String keyword);
}
