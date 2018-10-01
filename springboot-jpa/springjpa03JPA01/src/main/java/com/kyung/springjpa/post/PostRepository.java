package com.kyung.springjpa.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository // 이를 붙이면 중복으로 일어나는 것이다(있으나 없으나 상관 없다.)
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleStartsWith(String title);

//    @Query("SELECT p FROM Post AS p WHERE p.title = ?1") // nativrQuery 를 사용하고 싶다면 nativeQuery=true 를 주면 좋다.
//    List<Post> findByTitle(String title, Sort sort); // naemed query / 혹으 query 로 사용할 수 있다.

    // Query 사용시 안에 parameter 를 넣을 수 있다.(Named Parameter)
//    @Query("SELECT p FROM Post AS p WHERE p.title = :title")
//    List<Post> findByTitle(@Param("title") String keyword, Sort sort);

    // Query 사용시 entity name 을 참조해서 넣을 수 있다.(SpEL)
    @Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String keyword, Sort sort);

    // ======================== 권장하지 않는다. (콜백도 안되고 좀 불펴하다.) => 그냥 로직으로 작성하길...
    // update 는 modify 를 주어야 한다.
    @Modifying(clearAutomatically = true) // update 쿼리 실행 후에 persistent context 를 flush 해준다.
    @Query("UPDATE Post p SET p.title = ?1 WHERE p.id = ?2")
    int updateTitle(String title, Long id);
    // ========================
}
