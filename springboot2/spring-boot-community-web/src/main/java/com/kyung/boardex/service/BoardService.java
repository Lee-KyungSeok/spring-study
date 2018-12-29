package com.kyung.boardex.service;

import com.kyung.boardex.domain.Board;
import com.kyung.boardex.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public Page<Board> findBoardList(Pageable pageable) {
        // pageable 로 넘어온 객체가 0 이하면 0으로, 기본 페이지 크기인 10 으로 새로운 PageRequest 객체를 만들어 페이징 처리된 게시글 리스트 반환
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());

        return boardRepository.findAll(pageable);
    }

    public Board findBoardByIdx(Long idx) {
        return boardRepository.findById(idx).orElse(new Board());
    }
}
