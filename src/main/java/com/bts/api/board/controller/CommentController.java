package com.bts.api.board.controller;

import com.bts.api.board.domain.Comment;
import com.bts.api.board.domain.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.CustomPostsRepositoryImpl;
import com.bts.api.board.repository.PostsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Api(value = "CommentController")
public class CommentController {
    private final PostsRepository postsRepository;
    private final CustomPostsRepositoryImpl customPostsRepository;
    private final CommentRepository commentRepository;

    @ApiOperation(value = "댓글 작성하기")
    @RequestMapping(value = "/post/{p_id}", method = RequestMethod.POST)
    public Mono<ResponseEntity<Posts>> postTheComment(@PathVariable(value = "p_id") String p_id,
                                                      @RequestBody Comment comment) {
        //1. 댓글을 먼저 comment db 에 저장
        //2. 매핑된 url로 댓글을 등록할 post 를 찾고, post 도메인의 commentList 에 댓글을 저장한다.
        return this.commentRepository.save(comment)
                .then(this.postsRepository.findById(p_id).flatMap(i -> {
                            i.setCommentList(i.getCommentList(), comment);
                            return this.postsRepository.save(i);
                        })
                        .map(ResponseEntity::ok)
                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
