package com.kyung.boardex.resolver;

import com.kyung.boardex.annotation.SocialUser;
import com.kyung.boardex.domain.User;
import com.kyung.boardex.domain.enums.SocialType;
import com.kyung.boardex.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // HandlerMethodArgumentResolver 가 해당하는 파라미터를 지원할지 여부를 반환( true 반환 시 resolveArgument 메서드가 수행됨)
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 해당하는 어노테이션 타입이 명시되어 있는지 확인 (SocialUser 어노테이션이 있고 타입이 User 인 파라미터만 true 를 반환한다.)
        return parameter.getParameterAnnotation(SocialUser.class) != null &&
                parameter.getParameterType().equals(User.class);
    }

    // 파라미터 인잣값에 대한 정보를 바탕으로 실제 객체를 생성하여 해당 파라미터 객체에 바인딩
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 인증된 세션을 가져온다.
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();

        // user 의 key 로 저장한 session 을 가져온다.
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }

    // 인증된 User 객체를 만드는 메인 메서드
    private User getUser(User user, HttpSession session) {
        // session 에 user 가 없는 경우에 session 을 만들어서 저장한다.
        if(user == null) {
            try {
                // SecurityContextHolder 에서 인증된 정보를 OAuth2AuthenticationToken(엑세스 Token 도 제공한다는 의미로 이름이 이렇게 됨) 의 형태로 가져온다.
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

                // 리소스 서버에서 받아온 개인정보를 가져온다.
                Map<String, Object> map = authentication.getPrincipal().getAttributes();

                // 소셜 미디어 타입에 따라 User 륿 다르게 가져온다. (커스텀)
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);

                // user 가 DB 에 있는 경우에는 이를 가져와서 세션을 만들고 없는 경우에는 db 에 저장한다.
                user = userRepository.findByEmail(convertUser.getEmail());
                if (user == null) {
                    user = userRepository.save(convertUser);
                }

                // 인증된 authentication 이 권한을 갖고 있는지 체크하는 용도
                setRoleIfNotSame(user, authentication, map);

                // 세션을 "user" 로 저장
                session.setAttribute("user", user);
            } catch (ClassCastException e) {
                return user;
            }
        }

        return user;
    }


    // 소셜 미디어 타입에 따라 User 객체를 다르게 만든다.
    private User convertUser(String authority, Map<String, Object> map) {

        if(SocialType.FACEBOOK.getValue().equals(authority))
            return getModernUser(SocialType.FACEBOOK, map);
        else if(SocialType.GOOGLE.getValue().equals(authority))
            return getModernUser(SocialType.GOOGLE, map);
        else if(SocialType.KAKAO.getValue().equals(authority))
            return getKaKaoUser(map);

        return null;

    }

    private User getModernUser(SocialType socialType, Map<String,Object> map) {
        return User.builder()
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createdDate(LocalDateTime.now())
                .build();
    }

    // 카카오의 경우 다르게 가져온다.(최근에는 email 을 가져오지 않을 수도 있다.)
    private User getKaKaoUser(Map<String,Object> map) {
        HashMap<String, String> propertyMap = (HashMap<String, String>)map.get("properties");

        return User.builder()
                .name(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(SocialType.KAKAO)
                .createdDate(LocalDateTime.now())
                .build();
    }

    // 저장된 User 권한이 없으면 SecurityContextHolder 를 사용하여 해당 소셜 미디어 타입으로 권한을 저장
    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String,Object> map) {

        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            map,
                            "N/A",
                            AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())
                    )
            );
        }
    }
}
