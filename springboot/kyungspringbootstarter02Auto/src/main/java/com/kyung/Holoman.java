package com.kyung;

public class Holoman {

    String name;
    int howLong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }

    @Override
    public String toString() {
        return "Holoman{" +
                "name='" + name + '\'' +
                ", howLong=" + howLong +
                '}';
    }
}

/* 모듈 이름 설정하기
    - Xxx-Spring-Boot-Autoconfigure 모듈: 자동 설정
    - Xxx-Spring-Boot-Starter 모듈: 필요한 의존성 정의
    - 그냥 하나로 만들고 싶을 때는 => Xxx-Spring-Boot-Starter

   자동 설정 만들기
    - 1. 의존성 추가
    - 2. @Configuration 파일 작성
    - 3. src/main/resource/META-INF에 spring.factories 파일 만들기
    - 4. spring.factories 안에 자동 설정 파일 추가
    - 5. mvn install

   자동 설정 사용하기 (다른 프로젝트에서)
    - 1. 의존성에 추가
        //     <groupId>com.kyung</groupId>
        //    <artifactId>kyung-spring-boot-starter2</artifactId>
        //    <version>1.0-SNAPSHOT</version>
        와 같이 세줄을 복사해서 사용할 수 있다.
    - 2. ApplicationRunner 를 상속받아서 Autowired 로 의존성을 주입하여 사용할 수 있다.

   => 문제1> Bean 을 직접 등록하게 될 경우 무시될 수 있으므로 주의해야 한다.(의존성에 있는 것이 적용된다.)
   => 해결빙밥
    - Configuration 클래스의 Bean 에 ConditionalOnMissingBean 의 조건을 적용하여 빈이 없을 때 적용함을 설정한다.

   => if> Bean 을 각각 등록하고 싶지 않고 properties 를 이용하고 싶다면?
    - 1. 프로퍼티로 사용할 클래스를 생성하고 @ConfigurationProperties(“holoman”) 를 통해 프로퍼티 설정
    - 2. 프로퍼티 키값 자동완성 위한 dependency 설정
    - 3. @EnableConfigurationProperties(HolomanProperties.class) 를 통해 사용할 프로퍼티를 등록

    - 4. 다른 프로젝트에서 main/resources/application.properties 생성
    - 5. holoman.name = xxx
         holoman.how-long = xxx
         를 쓰고
 */
