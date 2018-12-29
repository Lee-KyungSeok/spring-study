package com.kyung.boardex.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 소셜미디어에 인증된 User 를 가져온다는 사실을 더 명확하게 표현하기 위해 어노테이션을 사용함
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocialUser {

}
