package com.kyung.springjpa.post;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post extends AbstractAggregateRoot<Post> { // @DomainEvents 와 @AfterDomainEventPublication 이 구현되어 있다.(이벤트를 쌓아놨다다 끝날때 비우게 됨)

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob // 255가 넘을것 같은 경우 lob 을 준다.
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Post publish() {
        this.registerEvent(new PostPublishedEvent(this)); // 이벤트를 등록할 수 있다.
        return this;
    }
}
