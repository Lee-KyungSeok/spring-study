package com.kyung.springjpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

// 모든 entity 에 적용시킬 거
@NoRepositoryBean // 중간에 사용되는 Repository 는 반드시 이 어노테이션을 사용할 것
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean contains(T entity);
}
