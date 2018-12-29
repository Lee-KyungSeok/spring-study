package com.kyung.boardex.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class ClientResources {

    // 해당 필드가 단일값이 아닌 중복으로 바인딩 된다고 표시하는 어노테이션 (세 곳의 프로퍼티를 바인팅하므로 이를 설정)
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client =
                new AuthorizationCodeResourceDetails(); // 각 소셜의 프로퍼티 중 client 를 기준으로 하위의 키/값을 매핑해주는 대상 객체

    @NestedConfigurationProperty
    private ResourceServerProperties resource =
            new ResourceServerProperties(); // oauth2 리소스값을 매핑하는데 이용 => 예제에서는 회원 정보를 얻는 userInfoUri 값을 받는데 사용

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
