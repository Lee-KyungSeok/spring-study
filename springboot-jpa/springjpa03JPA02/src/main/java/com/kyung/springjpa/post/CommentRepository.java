package com.kyung.springjpa.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// JpaSpecificationExecutor 를 추가한다.
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment>, QueryByExampleExecutor<Comment> {

    @EntityGraph(attributePaths = "post")
    Optional<Comment> getById(Long id);

    // 커스텀한 쿼리에도 Transactional 을 줄 수 있다.
    // 보통은 여러 repository 를 관리하는 서비스 계층에서 transactional 을 달고 쓴다.
    @Transactional(readOnly = true) // readonly 이면 flush 모드가 never 라 dirty checking 을 하지 않는다.
    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
