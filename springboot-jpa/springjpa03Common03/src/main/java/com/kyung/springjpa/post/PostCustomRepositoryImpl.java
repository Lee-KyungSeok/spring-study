package com.kyung.springjpa.post;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

// 네이밍 컨벤션 중요하다(impl !!!)
@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository<Post> { // 커스텀한 레파지토리를 상속받아서 만들 수 있다.

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Post> findMyPost() {
        System.out.println("custom findMyPost");
        return entityManager.createQuery("SELECT p FROM Post AS p", Post.class).getResultList();
    }

    @Override
    public void delete(Post entity) {
        System.out.println("custom delete");
        entityManager.remove(entity);
    }
}
