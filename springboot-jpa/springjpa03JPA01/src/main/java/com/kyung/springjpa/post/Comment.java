package com.kyung.springjpa.post;

import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;

//@NamedEntityGraph(name = "Comment.post",
//        attributeNodes = @NamedAttributeNode("post")) // 여기에 설정한 값은 eager 모드로 가져오게 되고 나머지는 설정한 값으로 가져오게 된다.
@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 를 설정하면 나중에 가져오게 된다.
    private Post post;

    private int up;

    private int down;

    private boolean best;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }
}
