package com.kyung.boardex.oauth;

import com.kyung.boardex.domain.enums.SocialType;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.Map;

// 3 개의 소셜 미디어 정보를 SocialType 을 기준으로 관리하므로 이를 커스터마이징 해야 한다.
public class UserInfoTokenService extends UserInfoTokenServices {

    public UserInfoTokenService(ClientResources resources, SocialType socialType) {
        // 각각의 소셜 미디어 정보를 주입할 수 있도록 설정함
        super(resources.getResource().getUserInfoUri(), resources.getClient().getClientId());
        setAuthoritiesExtractor(new OAuth2AuthoritiesExtractor(socialType));
    }

    // 3개의 소셜미디어 권한을 생성
    public static class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

        private String socialType;

        public OAuth2AuthoritiesExtractor(SocialType socialType) {
            this.socialType = socialType.getRoleType(); // ROLE_FACEBOOK 과 같은 형식으로 권한 생성방식 설정
        }

        // 권한을 리스트 형식으로 생성하여 반환
        @Override
        public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
            return AuthorityUtils.createAuthorityList(this.socialType);
        }
    }
}
