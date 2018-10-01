package com.kyung.springjpa.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 이렇게 설정하면 eager 모드로 post 를 가져오게 된다.
//    @EntityGraph(value = "Comment.post")
    @EntityGraph(attributePaths = "post") // 이처럼 여기서만 정의할 수도 있다. (위는 entity 에서도 저의해야 함)
    Optional<Comment> getById(Long id);

    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
