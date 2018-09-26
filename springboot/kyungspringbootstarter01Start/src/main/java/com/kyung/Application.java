package com.kyung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 스프링 부트 프로젝트 만들기 1
    - 1. maven (gradle) 프로젝트 생성
    - 2. https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#getting-started-maven-installation
    -    위 링크와 같이 메이븐 설정
    - 3. application 클래스를 만들고 실행한다.

   스프링 부트 프로젝트 만들기 2
    - 1. https://start.spring.io 에서 프로젝트 생성 가능

   스프링 부트 프로젝트 만들기 3
    - 1. 콘솔을 다운받아서 설정하기

   참고1>
    - terminal 을 연 뒤
    - $ mvn package (패키징을 한다. - jar 파일을 생성)
    - $ java -jar target/ (jar 파일 실행)
    - 그러면 웹 어플리케이션이 실행된다.

   참고2> 어플리케이션 클래스는 디폴트 패키지 가장 상단에 위치시키는 것을 추천한다.
    - 이 패키지를 루트로 사용하겠다는 뜻이다.
*/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/* SpringBootApplication 는 아래의 작업을 자동으로 해준다.
    - 빈은 사실 두 단계로 나눠져 있다.

   1. @ComponentScan (빈을 모두 읽는다.)
    - @Component
    - @Configuration @Repository @Service @Controller @RestController

    => 컴포넌트(@Configuration 등) 애너테이션을 가진 클래스를 빈으로 등록한다.

   2. @EnableAutoConfiguration (추가적인 빈을 읽는다.)
    - spring.factories (org.springframework.boot.autoconfigure.EnableAutoConfiguration)
    - @Configuration
    - @ConditionalOnXxxYyyZzz

    => 메타파일들을 빈으로 등록한다.
    => 결국 Configuration 으로 등록되어 있는 것을 빈으로 등록
    => 그런데 이는 ConditionalXXXX, 조건에 따라 사용할지 안할지 결정하고 등록하게 된다.
 */


