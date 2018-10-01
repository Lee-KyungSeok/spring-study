package com.kyung.springjpa.post;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountAuditAware implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        // 원래는 spring-security 를 설정하고 진행해야 한다.
        System.out.println("looking for current user");
        return Optional.empty();
    }
}
