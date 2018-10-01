package com.kyung.springjpa.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository // 이를 붙이면 중복으로 일어나는 것이다(있으나 없으나 상관 없다.)
public interface PostRepository extends JpaRepository<Post, Long> {

}
