package com.bts.api.board.repository;

import com.bts.api.board.domain.Board;
import com.bts.api.board.domain.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {
    private final ReactiveMongoTemplate mongoTemplate;
    private final BoardRepository boardRepository;

    //글 작성 -> 글 등록 -> 제목, 작성자, 등록 시간만을 가지고 글목록(Board) 생성
    @Override
    public Mono<Board> insertValue(Posts posts) {
        Board insertValue = Board.builder()
                .title(posts.getTitle())
                .author(posts.getAuthor())
                .createPostDate(posts.getCreatePostDate())
                .build();
        return mongoTemplate.insert(insertValue);
    }
}
