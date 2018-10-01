package com.kyung.springjpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

// Repository 를 커스텀할 수 있다. (제공하고 싶은 기능만 제공할 수 있다)
//@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // 베이스를 상속바았다면 이는 필요 없다.
public interface CommentRepository extends MyRepository<Comment, Long> {
    // Query Strategy
    // 1. 메서드 이름을 분석해서 쿼리 만들기
    List<Comment> findByCommentContains(String keyword);

    // 2.Query 를 직접 지정 (미리 정의하기)
    //  - native query 를 사용하고 싶다면 아래처럼 속성을 주면 된다.
    @Query(value = "SELECT c FROM Comment AS c", nativeQuery = true)
    List<Comment> findByCustom(String keyaword);

    // 3. 미리 정의한 쿼리를 찾아보고 없으면 만들기 (이게 디폴트다)

    // 쿼리 만드는 법
    // 리턴타입 {접두어}{도입부}By{프로퍼티 표현식}(조건식)[(And|Or){프로퍼티 표현식}(조건식)]{정렬 조건} (매개변수)
    Page<Comment> findByLikeCountGreaterThanAndPostOrderByCreatedDesc(int likeCount, Post post, Pageable pageable); // 어떤 한 post 에 있으면서 likeCount 보다 큰 값을 찾겠다. (단, page 로 받고 싶으면 Pageable 을 받아야 함)
}
