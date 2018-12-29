package com.community.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 권한을 다르게 주기 위해 사용 (여기서는 @PreAuthorize 와 @PostAuthorize 를 사용하기 위해 붙임)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // 테스트 위해 두개의 유저를 만들었다(인메모리 형식으로.. 그래야 사라짐)
    @Bean
    InMemoryUserDetailsManager userDetailsManager() {
        // 일반 유저 생성
        User.UserBuilder commonUser = User.withUsername("commonUser").password("{noop}common").roles("USER");
        // admin 유저 생성
        User.UserBuilder havi = User.withUsername("havi").password("{noop}test").roles("USER", "ADMIN");

        // 유저를 만들고 저장
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(commonUser.build());
        userDetailsList.add(havi.build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration configuration = new CorsConfiguration();
        // 모든 경로에 대하여 허용
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);

        // 설정한 경로를 세팅한다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        http.httpBasic()
            .and()
                .authorizeRequests()
                .anyRequest().permitAll()
            .and()
                .cors().configurationSource(source) // cors 를 시큐리티 설정에 추가
            .and()
                .csrf().disable();


    }
}
