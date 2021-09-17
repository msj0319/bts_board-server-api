package com.bts.api.board.repository;

import com.bts.api.board.domain.Board;
import com.bts.api.board.domain.Posts;
import reactor.core.publisher.Mono;

public interface CustomBoardRepository {
    Mono<Board> insertValue(Posts posts);
}
