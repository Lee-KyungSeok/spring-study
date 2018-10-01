package com.kyung.springjpa;

import com.sun.istack.internal.NotNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

// 공통적으로 적용하고 싶은 repository 생성 가능
// 원하는 것만 먼저 base 로 설정해서 사용할 수 있다.
@NoRepositoryBean
public interface MyRepository<T, Id extends Serializable> extends Repository<T, Id> {
    <E extends T> E save(@NotNull E entity); // runtime 에 null 체크를 해준다.

    List<T> findAll();

    long count();

    <E extends T> Optional<E> findById(Id id);
}
