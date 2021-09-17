package com.bts.api.board.controller;

import com.bts.api.board.domain.Board;
import com.bts.api.board.domain.Posts;
import com.bts.api.board.repository.BoardRepository;
import com.bts.api.board.repository.CustomBoardRepositoryImpl;
import com.bts.api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final CustomBoardRepositoryImpl customBoardRepository;
    private final BoardRepository boardRepository;

    //글 등록하기 버튼 -> /board/{id} -> 글 전체 내용 출력 시나리오
    @RequestMapping(value = "/board/{id}", method = RequestMethod.GET)
    public Mono<Posts> getThePosts(@PathVariable("id") String id) {
        return postRepository.findById(id);
    }

    //글 등록하기 -> /board url 에선 글 제목, 글쓴이, 댓글 수가 리스트화 되어 간략하게 출력됨
    @RequestMapping(value = "/board", method = RequestMethod.POST)
    public Mono<Board> createThePost(@RequestBody Posts posts) {
        return postRepository.save(posts).then(customBoardRepository.insertValue(posts));
    }
}
