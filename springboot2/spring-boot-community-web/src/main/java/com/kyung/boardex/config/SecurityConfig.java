package com.kyung.boardex.config;

import com.kyung.boardex.domain.enums.SocialType;
import com.kyung.boardex.oauth.ClientResources;
import com.kyung.boardex.oauth.UserInfoTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity // 웹 시큐리티 기능을 사용하겠다는 어노테이션
@EnableOAuth2Client // oauth2 client 기능을 이용
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 커스텀 하기 위해 상속받고 configure 를 오버라이딩 한다.

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
            .authorizeRequests() // 인증 메커니즘을 요청한 HttpServletRequest 기반으로 설정
                .antMatchers("/", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**") // 요청 패턴을 설정
                    .permitAll() // 설정한 request 패턴을 누구나 접근할 수 있도록 허용
                .antMatchers("/facebook").hasAuthority(SocialType.FACEBOOK.getRoleType()) // 해당 권한을 지닌 사용자만 경로를 사용할수 있도록 통제
                .antMatchers("/facebook").hasAuthority(SocialType.GOOGLE.getRoleType())
                .antMatchers("/facebook").hasAuthority(SocialType.KAKAO.getRoleType())
                .anyRequest() // 설정한 요청 이외의 request 요청을 표현
                    .authenticated() // 해당 요청을 인증된 사용자만 사용 가능
            .and()
                .headers() // 응답에 해당하는 header 를 설정 (설정하지 않을 시 디폴트로 설정)
                .frameOptions().disable() // XFrameOptionsHeaderWriter 의 최적활 설정을 허용하지 않는다.
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")) // 인증의 진입 지점 설정
            .and()
                .formLogin().successForwardUrl("/board/list") // 로그인 성공시 설정된 경로로 포워딩
            .and()
                .logout() // 로그아웃 설정
                .logoutUrl("/logout") // 수행될 경로 설정
                .logoutSuccessUrl("/") // 성공시 포워딩될 결로 설정
                .deleteCookies("JSESSIONID") // 삭제할 쿠키 설정
                .invalidateHttpSession(true) // 세션의 무효화 설정
            .and()
                .addFilterBefore(filter, CsrfFilter.class) // 먼저 시작도리 필터 등록(filter 보다 CsrfFilter 를 먼저 실행하도록 설정)
                .addFilterBefore(oauth2Filter(), BasicAuthenticationFilter.class) // 기본 인증필터를 적용하고 커스텀 필터 적용
                .csrf().disable();
    }

    // OAuth2 클라이언트용 시큐리티 필터를 불러와서 올바른 순서로 필터가 동작하도록 설정 (스프링 시큐리티 필터가 실행되기 전에 충분히 낮은 순서로 필터를 등록)
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }


    // 세 가지의 소셜 로그인의 필터를 적용하여 리턴한다.
    private Filter oauth2Filter() {
        CompositeFilter filter = new CompositeFilter();

        List<Filter> filters = new ArrayList<>();
        filters.add(oauth2Filter(facebook(), "/login/facebook", SocialType.FACEBOOK));
        filters.add(oauth2Filter(facebook(), "/login/google", SocialType.GOOGLE));
        filters.add(oauth2Filter(facebook(), "/login/kakao", SocialType.KAKAO));

        filter.setFilters(filters);
        return filter;
    }

    // 각각의 소셜 로그인의 필터를 생성한다.
    private Filter oauth2Filter(ClientResources client, String path, SocialType socialType) {
        // OAuth2 클라이언트용 인증 처리 필터 생성
        OAuth2ClientAuthenticationProcessingFilter filter =
                new OAuth2ClientAuthenticationProcessingFilter(path);
        // 권한서버와의 통신을 위한 restTemplate 을 생성
        OAuth2RestTemplate template =
                new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);

        filter.setRestTemplate(template);

        // User의 권한 최적화
        filter.setTokenServices(new UserInfoTokenService(client, socialType));

        // 인증 성공/실패시 필터에 리다이렉트될 URL 설정 (성공시 User 에 대한 정보를 챙겨왔다는 것을 의미한다 => 특정한 설정이 없다면 SecurityContextHolder 에 정보가 저장되어 있다.)
        filter.setAuthenticationSuccessHandler((request, response, authentication) ->
            response.sendRedirect("/" + socialType.getValue() + "/complete"));
        filter.setAuthenticationFailureHandler((request, response, exception) ->
            response.sendRedirect("/error"));

        return filter;
    }



    // 3개의 소셜 미디어 프로퍼티를 등록(application.yml 파일의 프로퍼티 등록)
    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("kakao")
    public ClientResources kakao() {
        return new ClientResources();
    }
}
