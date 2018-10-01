package com.kyung.springjpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void save() {
        Post post = new Post();
        post.setTitle("jpa");
        postRepository.save(post); // 중복된 post 인 경우 update(merge) 를 날리게 된다.

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void findByTitleStartsWith() {
        savePost("Spring Data Jpa");
        List<Post> all = postRepository.findByTitleStartsWith("Spring");
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitle() {
        savePost("Spring Data Jpa2");
        // Sort 에는 property 혹은 alias 만 사용 가능하다.
//        List<Post> all = postRepository.findByTitle("Spring", Sort.by("title"));
        // 아래처럼 sort 에 함수를 넣고 싶다면 우회할 수 있다.
        List<Post> all = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));

        assertThat(all.size()).isEqualTo(1);
    }

    private Post savePost(String title) {
        Post post = new Post();
        post.setTitle(title);
        return postRepository.save(post);
    }

    @Test
    public void updateTitle() {
        Post spring = savePost("Spring Data Update!");
        String hibernate = "hibernate";

        /* 아래는 update 쿼리 메서드를 사용했는데 캐싱 때문, 콜백 등 권장하지 않고 로직으로 작성하길 권장한다.
        int update = postRepository.updateTitle(hibernate, spring.getId());
        assertThat(update).isEqualTo(1);

        Optional<Post> byId = postRepository.findById(spring.getId());
        Post post = byId.get();
        assertThat(post.getTitle()).isEqualTo(hibernate);
        */

        // 아래를 더 권장한다. (DB 가 update 를 해준다.)
        spring.setTitle(hibernate);
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(hibernate);

    }


}