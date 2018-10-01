package com.kyung.springjpa.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// 컴파일 까지 끝난 후 QuerydslPredicateExecutor 를 상속받으면 코드에서 predicate 를 사용할 수 있게 된다.
// 주의할 점은 이 QuerydslPredicateExecutor 의 구현체는 simplerepository 이므로 커스텀해서 내꺼를 사용하면 구현체가 없어서 사용할 수 없게 된다.
// => 이때 상속 구조를 simplerepository 가 아니라 querydsljparepository 로 상속받아 이용하면 된다.
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

}
