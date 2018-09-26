package com.kyung.springbootdata.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 아래와 같이 interface 로 지정하면 데이터를 가져오게 할 수 있다.
    // 또한 Optional 을 줄 수 있다.
    Optional<Account> findByUsername(String username);

    // 만약 native query 를 사용하고 싶다면 아래와 같이 어노테이션을 주면 된다.
    @Query(nativeQuery = true, value = "SELECT * FROM account WHERE username = '{0}'")
    Account findByUsernameCustom(String username);
}
