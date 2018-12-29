package com.kyung.boardex.repository;

import com.kyung.boardex.domain.Board;
import com.kyung.boardex.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
