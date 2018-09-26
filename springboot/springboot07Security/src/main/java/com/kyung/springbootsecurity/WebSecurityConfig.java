package com.kyung.springbootsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // spring 부트와 거의 동일하게 작동하는 webSecurity 설정 사용 가능
    // 단, User login 과 같은 것은 UserDetailsServiceAutoConfiguration 에 있으므로 설정되지는 않음 (자동 어드민(유저) 를 만들어 주지는 않는다.)
    // - 즉, spring Sercurity 가 아니라 spring-boot 가 지원하는 기능은 딱히 쓸 일이 없다. (위의 유저만 추가해주는거밖에 없다고 봐도 무방하다.)
}
