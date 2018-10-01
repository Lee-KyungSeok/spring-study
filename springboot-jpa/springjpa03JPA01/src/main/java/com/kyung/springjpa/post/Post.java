package com.kyung.springjpa.post;

import javax.persistence.*;
import java.util.Date;

@Entity
//@NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post AS p WHERE p.title = ?1") // jpql, 하지만 이보다는 repository 에서 쿼리를 지정하는게 낫다.
//@NamedNativeQuery() // NativeQuery 로 만들 수 있다.
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
