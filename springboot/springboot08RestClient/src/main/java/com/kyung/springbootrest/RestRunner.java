package com.kyung.springbootrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

    // RestTemplate 은 blocking io 기반
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    // webclient 는 non-blocking io 기반
    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RestTemplate restTemplate = restTemplateBuilder.build();
        WebClient webClient = webClientBuilder
//                .baseUrl("http://localhost:8080") // 기본 url 설정 가능 (그러나 전역으로 선언했으므로 주석처리)
                .build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /* 아래는 restTemplate 으로 hello 호출되기 전에 world 가 호출되지 않는다.
        // hello 호출
        String helloResult = restTemplate.getForObject("http://localhost:8080/hello", String.class);
        System.out.println(helloResult);

        // world 호출
        String worldResult = restTemplate.getForObject("http://localhost:8080/world", String.class);
        System.out.println(worldResult);
        */

        // hello 를 webclient 로 호출
        Mono<String> helloMono = webClient.get().uri("/hello")
                .retrieve()
                .bodyToMono(String.class);
        // subscribe 해줘야 실행된다.
        helloMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();

        });

        // world 를 webclinet 로 호출
        Mono<String> worldMono = webClient.get().uri("/world")
                .retrieve()
                .bodyToMono(String.class);
        // subscribe 해줘야 실행된다.
        helloMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();

        });
    }
}
