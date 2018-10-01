package com.kyung.springjpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud() {
        // Optional 을 이용해서 없다면 비어있는 콜렉션을 리턴하게 된다.
        Optional<Comment> byId = commentRepository.findById(100l);
        assertThat(byId).isEmpty();
//        Comment comment = byId.orElse(new Comment()); // Optional 은 이런식으로 사용하 수 있다.

        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll(); // 참고로 jpa 에서 list 가 없다면 null 이 아니라 비어있는 list 를 리턴해준다.
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(1);

        long count = commentRepository.count();
        assertThat(count).isEqualTo(1);

    }
}