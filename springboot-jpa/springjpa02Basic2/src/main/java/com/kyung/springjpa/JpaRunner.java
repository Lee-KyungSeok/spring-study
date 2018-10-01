package com.kyung.springjpa;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    // 아래처럼 repository 를 주입받아 사용할 수 있다.
    @Autowired
    PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        // Transient 상태 (jpa 가 모르는 상태) =============================
        Account account = new Account();
        account.setUsername("kyungseok");
        account.setPassword("jpa");

        Study study = new Study();
        study.setName("Spring Data JPA");

        account.addStudy(study);
        // ==========================================================

        // persistent 상태 (jpa 가 관리중)=============================
        Session session = entityManager.unwrap(Session.class);
        session.save(account); // 언제 저장할지는 jpa 가 정한다,
        session.save(study);

        // 이는 select 쿼리가 발생하지 않는다.(why? 캐시가 존재하니까 => 즉, jpa 가 관리중)
        Account kyungseok = session.load(Account.class, account.getId());
        kyungseok.setUsername("cory"); // insert 보다 이게 먼저 일어나서 insert 는 한번만 일어나게 된다.
        // ==========================================================
        */

        /*
        Post post = new Post();
        post.setTitle("title!!!");

        Comment comment = new Comment();
        comment.setComment("코멘트입니다.");
        post.addComment(comment);


        Comment comment1 = new Comment();
        comment1.setComment("코멘트 두번째 입니다.");
        post.addComment(comment1);


        Comment comment2 = new Comment();
        comment2.setComment("코멘트 번째 입니다.");
        post.addComment(comment2);

        Session session = entityManager.unwrap(Session.class);
        session.save(post);
        */

        // Fetch 를 잘못 사용하면 n+1 문제가 일어날 수 있으므로 적절히 조절해야 한다.
        // 다만... 여기서는 일어나지 않았다 ㅎㅎㅎ (getComments 하니 다 가져옴 ㅎㅎ)
        Session session = entityManager.unwrap(Session.class);
        Post post = session.get(Post.class, 4l);
        System.out.println("====================");
        System.out.println(post.getTitle());

        post.getComments().forEach(c -> {
            System.out.println("--------------------");
            System.out.println(c.getComment());
        });

        // 아래와 같이 쿼리를 만들 수 있다. (이는 entity 기준으로 작성한다.)
        // jpql 사용 방법 (하지만... 타입 세이프하지 않다,)
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
        List<Post> posts = query.getResultList();
        posts.forEach(System.out::println);

        // 타입 세이프하게 만들기(조금 더러움)
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root);
        List<Post> postsCriteria = entityManager.createQuery(criteriaQuery).getResultList();
        postsCriteria.forEach(System.out::println);

        // native 쿼리
        List<Post> postNative = entityManager.createNativeQuery("SELECT * from Post", Post.class)
                .getResultList();
        postNative.forEach(System.out::println);

    }
}
