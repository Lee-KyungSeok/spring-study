package com.kyung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringInitApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SpringInitApplication.class);
//        app.setBannerMode(Banner.Mode.OFF); // 베너 끄기

        // 베너 커스텀 (텍스트파일이 있으면 그게 적용됨)
        app.setBanner((environment, sourceClass, out) -> {
            out.println("==============================");
            out.println("banner test coding");
            out.println("==============================");
        });

        // 리스너를 등록
        app.addListeners(new StartingListener());

        // 어플리케이션을 REACTIVE 로 설정
        app.setWebApplicationType(WebApplicationType.REACTIVE);
        app.run(args);
    }
}

/* 기본 로그 레벨은 INFO 이다.
    - Edit Configuration 에 vm 에 -Debug 로 적거나 프로퍼티에 --debug 를 적용하면 디버그 모드로 실행시킬 수 있다.
*/

/* FailureAnalyzer
    - 에러 메세지를 이쁘게 해줌
    - 딱히 커스텀하지는 않는다.
 */

/* 베너
    - 베너를 바꾸고 싶으면 banner.txt 에 적용해서 바꿀 수 있다. (gif, jpg, png 등도 사용 가능)
    - ${spring-boot.version} 등의 변수를 사용 가능
    - Banner 클래스 구현하고 SpringApplication.setBanner()로도 설정 가능
    - Application 객체에서 app.setBannerMode(Banner.Mode.OFF) 로 베너를 끌 수 있다.
    - setBanner 로 베너를 구현할 수 있다.
 */

/* SpringApplicationBuilder
    - 아래와 같이 빌더 패턴으로 사용 가능
    - new SpringApplicationBuilder()
        .sources(SpringInitApplication.class)
        .banner((environment, sourceClass, out) -> { out.println("banner");})
        .run(args)
 */

/* WebApplicationType
    - SERVLET : SpringMVC 면 기본적으로 실행됨 (서블릿이 있으면 항상 이로 실행된다.)
    - REACTIVE : SpringWebFlux 가 들어있으면 이게 실행됨
    - NONE : 웹서버 X

 */