package com.bts.api.board.controller;

import com.bts.api.board.domain.Comment;
import com.bts.api.board.domain.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.PostsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Api(value = "CommentController")
public class CommentController {
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;

    @ApiOperation(value = "댓글 작성하기")
    @RequestMapping(value = "/board_post/{p_id}", method = RequestMethod.POST)
    public Mono<Comment> postTheComment(@PathVariable(value = "p_id") String p_id, @RequestBody Comment comment) {
        return this.commentRepository.save(comment);
    }
}
