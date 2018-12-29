package com.kyung.boardex;

import com.kyung.boardex.domain.Board;
import com.kyung.boardex.domain.User;
import com.kyung.boardex.domain.enums.BoardType;
import com.kyung.boardex.repository.BoardRepository;
import com.kyung.boardex.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {
    private final String boardTestTitle = "테스트";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init() {
        User user = userRepository.save(User.builder()
            .name("kyung")
            .password("test")
            .email(email)
            .createdDate(LocalDateTime.now())
            .build());

        boardRepository.save(Board.builder()
            .title(boardTestTitle)
            .subTitle("subTitle")
            .content("contents")
            .boardType(BoardType.free)
            .createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now())
            .user(user)
            .build());
    }

    @Test
    public void creationTest() {
        User user = userRepository.findByEmail(email);
        assertThat(user.getName()).isEqualTo("kyung");
        assertThat(user.getPassword()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo(email);

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle()).isEqualTo(boardTestTitle);
        assertThat(board.getSubTitle()).isEqualTo("subTitle");
        assertThat(board.getContent()).isEqualTo("contents");
        assertThat(board.getBoardType()).isEqualTo(BoardType.free);
    }
}
