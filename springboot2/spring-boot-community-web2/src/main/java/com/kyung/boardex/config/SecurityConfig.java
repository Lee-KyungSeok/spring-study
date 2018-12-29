package com.kyung.boardex.config;

import com.kyung.boardex.domain.enums.SocialType;
import com.kyung.boardex.oauth.CustomOAuth2Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity // 웹 시큐리티 기능을 사용하겠다는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 커스텀 하기 위해 상속받고 configure 를 오버라이딩 한다.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
            .authorizeRequests() // 인증 메커니즘을 요청한 HttpServletRequest 기반으로 설정
                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**") // 요청 패턴을 설정
                    .permitAll() // 설정한 request 패턴을 누구나 접근할 수 있도록 허용
                .antMatchers("/facebook").hasAuthority(SocialType.FACEBOOK.getRoleType()) // 해당 권한을 지닌 사용자만 경로를 사용할수 있도록 통제
                .antMatchers("/facebook").hasAuthority(SocialType.GOOGLE.getRoleType())
                .antMatchers("/facebook").hasAuthority(SocialType.KAKAO.getRoleType())
                .anyRequest() // 설정한 요청 이외의 request 요청을 표현
                    .authenticated() // 해당 요청을 인증된 사용자만 사용 가능
            .and()
                .oauth2Login() // 구글과 페이스북에 대한 OAuth2 인증 방식 적용(스프링부트에서 제공하는 OAuth2 사용 가능)
                .defaultSuccessUrl("/loginSuccess") // 성공시 Url
                .failureUrl("/loginFailure") // 실패시 Url
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
                .csrf().disable();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,  // 디폴트 client properties 를 불러온다.
            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) { // application.yml 에서 정의한 카카오 클라이언트 ID 를 불러온다

        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client)) // 구글,페이스북의 인증 정보를 빌드
                .filter(Objects::nonNull) // 인증정보가 없으면 뺀다.
                .collect(Collectors.toList()); // list 로 변환


        // 카카오 정보를 빌드
        ClientRegistration kakaoClientRegistration = CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") // null 이면 실행이 되지 않기 때문에 임의값 값 세팅
                .jwkSetUri("test") // null 이면 실행이 되지 않기 때문에 임의값 값 세팅
                .build();

        // registration 카카오 정보 추가
        registrations.add(kakaoClientRegistration);

        return new InMemoryClientRegistrationRepository(registrations);
    }

    // 구글과 페이스북의 인증 정보를 빌드
    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {

        if("google".equals(client)) {

            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile") // scope 를 지정
                    .build();
        }

        if("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");

            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link") // 그래프API 의 경우 scope 로 안되기 때문에 직접 파라미터를 넣는다.
                    .scope("email")
                    .build();
        }

        return null;
    }
}
