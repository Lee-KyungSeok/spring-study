package com.kyung.springjpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud() throws ExecutionException, InterruptedException {

        this.createComment(100, "spring data jpa");
        this.createComment(55, "hibernate spring");

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));
        Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10, pageRequest);
        assertThat(comments.getTotalElements()).isEqualTo(2);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100); // 첫번째 like 가 100 개

        // Stream 으로 받으려면 try catch (try-with-resource) 가 필요하다.
        try(Stream<Comment> streamComments = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring")) {
            Comment firstComment = streamComments.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(100);
        }

        // Async 를 사용하면 별도의 쓰레드에서 실행시키는 것(non-blocking 코드를 만들 수 있다 but> Reactive 랑은 다른 것) => Async 는 딱히 권장하고 싶지는 않다.
        ListenableFuture<List<Comment>> future = commentRepository.findByCommentContains("spring");
        future.isDone(); // 결과가 나왔는지 확인 가능
//        List<Comment> commentsAsync = future.get(); // Future 로 사용했을 경우는 이렇게 사용
        // ListenableFuture 사용시 아래처럼 사용 가능
        future.addCallback(new ListenableFutureCallback<List<Comment>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(List<Comment> comments) {
                comments.forEach(System.out::println);
            }
        });
    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setLikeCount(likeCount);
        newComment.setComment(comment);
        commentRepository.save(newComment);
    }
}