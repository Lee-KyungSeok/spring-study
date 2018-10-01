package com.kyung.springjpa.post;

import com.kyung.springjpa.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
//}

// 위는 커스텀 리파지토리, 아래는 기본 리포지토리를 커스터마이징 한 것이다.
public interface PostRepository extends MyRepository<Post, Long> {

}
