package com.kyung.springjpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class) // 테스트용 config 를 추가해 주어 리스너를 등
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    /* 이벤트를 구현했다면 이벤트를 만드는 것이 아니라 save 할 때 이벤트를 넣기만 하면 된다.
    @Test
    public void event() {
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);
        applicationContext.publishEvent(event);
    }
    */

    @Test
    public void crud() {
        Post post = new Post();
        post.setTitle("hibernate");

        // custom 한 것으로 사용 가능
        assertThat(postRepository.contains(post)).isFalse();

        postRepository.save(post.publish()); // 이렇게 publish 메서드를 만들어 넣어주면 자동으로 event 를 발생하게 할 수 있다.

        assertThat(postRepository.contains(post)).isTrue();

//        postRepository.findMyPost();

        postRepository.delete(post);
        postRepository.flush(); // delete 를 확인하기 위해 강제로 사용 (rollback 이라 delete 를 무시하는 경우가 생긴다)
    }
}