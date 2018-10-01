package com.kyung.springjpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest // 슬라이싱 테스트이므로... auditingAware bean 을 찾을 수 없게 된다... 통합테스트 환경으로 바꾸면 가능하다.
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getComment() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("spring data jpa projection");
        comment.setPost(savedPost);
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(savedPost.getId(), CommentSummary.class).forEach(c -> {
            System.out.println("=============================");
            System.out.println(c.getVotes());
        });

        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("=============================");
            System.out.println(c.getComment());
        });
    }

    @Test
    public void specs() {
        // 스펙을 정의할 수 있다.
        // page 로도 가져올 수 있다.
        Page<Comment> page = commentRepository
                .findAll(CommentSpecs.isBest().and(CommentSpecs.isGood()),
                        PageRequest.of(0, 10));
    }

    // 잘 쓰이지 않지만... Query By Example (굉장히 제한적이기 때문)
    @Test
    public void qbe() {
        Comment prove = new Comment();
        prove.setBest(true);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withIgnorePaths("up", "down");

        Example<Comment> example =  Example.of(prove, exampleMatcher);

        commentRepository.findAll(example);
    }
}