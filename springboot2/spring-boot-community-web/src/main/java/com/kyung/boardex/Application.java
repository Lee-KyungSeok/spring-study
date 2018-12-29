package com.kyung.boardex;

import com.kyung.boardex.domain.Board;
import com.kyung.boardex.domain.User;
import com.kyung.boardex.domain.enums.BoardType;
import com.kyung.boardex.repository.BoardRepository;
import com.kyung.boardex.repository.UserRepository;
import com.kyung.boardex.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    // 커스텀한 userArgumentResolver 를 등록한다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    // 일단 CommandLineRunner 를 이용해 한명의 user 를 생성하여 글 200 개를 넣었다.
    @Bean
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) {
        return (args) -> {
            // sample user 생성
            User user = userRepository.save(User.builder()
                    .name("kyung")
                    .password("test")
                    .email("kyung@test.com")
                    .createdDate(LocalDateTime.now())
                    .build());

            // 글 200개 생성
            IntStream.rangeClosed(1, 200).forEach(index -> {
                boardRepository.save(Board.builder()
                        .title("게시글 " + index)
                        .subTitle("순서 " + index)
                        .content("contents")
                        .boardType(BoardType.fee)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .user(user)
                        .build());
            });
        };
    }
}
