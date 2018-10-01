package com.kyung.springjpa.post;

import java.util.List;

public interface PostCustomRepository<T> {

    List<Post> findMyPost();

    // 기본 기능 덮어써서 사용할 수 있다.
    // (참고> delete 의 경우 비효율적이라고 주장하는 사람들도 있다, 하지만 cascade 등 성능 이외의 이슈가 존재한다.)
    public void delete(T entity);
}
