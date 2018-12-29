package com.community.rest.controller;

import com.community.rest.domain.Board;
import com.community.rest.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RepositoryRestController
public class BoardRestController {

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/boards")
    public @ResponseBody PagedResources<Board> simpleBoard(@PageableDefault Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);

        // 페이징 처리에 필요한 메타데이터 생성 (여기서는 전체 페이지 수, 현재 페이지 번호, 총 게시판 수로 구성함)
        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());

        // HATEOAS 를 적용하게 되는 PagedResources 를 생성(페이징값까지 생성된 REST 형의 데이터를 만든다.) => 중요(Restfull 데이터 생성)
        PagedResources<Board> resources = new PagedResources<>(boards.getContent(), pageMetadata);

        // 객체 생성 시 링크를 설정(아래는 Board 마다 상세정보를 불러올 수 있는 링크를 추가함)
        resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());

        return resources;
    }
}
