package com.kyung.springjpa;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Account")
//@Table // 이것이 생략된 것 (아무 설정 안하면 클래스 이름이 테이블이 된다.)
public class Account {

    // GeneratedValue 에도 규칙 등을 줄 수 있다.
    @Id @GeneratedValue
    private Long id;

    // Column 에 특정 속성을 지정할 수 있다.
    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String email;

    // 날짜를 지정할 수 있다. (현재는 time 과 calendar 밖에 지원 안하지만 이후 버전에서는 zone 등 지원한다.)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    private String yes;

    // transient 는 column 으로 매핑하지 않는다.
    @Transient
    private String no;

    @Embedded // Embeddable 로 만들어진 valuetype 은 embedded 어노테이션이 필요하다.
    @AttributeOverrides({ // 여러 종류의 address 가 있을 수 있고, 특정 타입을 바꿔서 매핑시킬 수 있다.
            @AttributeOverride(name = "street", column = @Column(name = "home_street"))
    })
    private Address homeAddress;

//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "street", column = @Column(name = "office_street"))
//    })
//    private Address officeAddress;

    @OneToMany(mappedBy = "owner") // account 하나에는 많은 study 들이 존재 (만약 양방향 관계라면 어떻게 매핑되어 있는지 여기서 설정해 주어야 한다.)
    private Set<Study> studies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    public void addStudy(Study study) {
        study.setOwner(this); // 양방향인 경우 여기서 설정해야 한다.(주의!!!)
        this.getStudies().add(study); // 양방향일 때는 이는 optional 이다.

    }

    // remove 할 때도 매핑해서 하도록 한다,
    public void removeStudy(Study study) {
        study.setOwner(null);
        this.getStudies().remove(study);

    }
}
