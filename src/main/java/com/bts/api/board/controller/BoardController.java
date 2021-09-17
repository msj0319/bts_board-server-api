package com.bts.api.board.controller;

import com.bts.api.board.domain.Board;
import com.bts.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final ReactiveMongoTemplate mongoTemplate;
    private final BoardRepository boardRepository;

    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public Flux<Board> getThePostList() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }
}
