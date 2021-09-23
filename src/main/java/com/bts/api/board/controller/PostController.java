package com.bts.api.board.controller;

import com.bts.api.board.domain.Comment;
import com.bts.api.board.domain.Posts;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.CustomPostsRepositoryImpl;
import com.bts.api.board.repository.PostsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Api(value = "PostController")
public class PostController {
    private final PostsRepository postRepository;
    private final CommentRepository commentRepository;
    private final CustomPostsRepositoryImpl customPostsRepository;

    @ApiOperation(value = "모든 게시물 전체 조회")
    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public Flux<Posts> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "p_id"));
    }

    @ApiOperation(value = "하나의 게시물 조회")
    @RequestMapping(value = "/post/{p_id}", method = RequestMethod.GET)
    public Mono<Posts> getThePosts(@PathVariable(value = "p_id") String id) {
        return postRepository.findById(id);
    }

    @ApiOperation(value = "게시물 작성")
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public Mono<ResponseEntity<Posts>> createThePost(@RequestBody Posts posts) {
        return postRepository.save(posts)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "게시물 수정하기")
    @RequestMapping(value = "/post/{p_id}", method = RequestMethod.PUT)
    public Mono<ResponseEntity<Posts>> updateThePost(@PathVariable(value = "p_id") String p_id,
                                                     @RequestBody Posts posts) {
        return this.postRepository.findById(p_id)
                .flatMap(i -> {
                    i.setTitle(posts.getTitle());
                    i.setContent(posts.getContent());
                    i.setModifiedPostDate(posts.getModifiedPostDate());
                    return postRepository.save(i);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "게시물 삭제 하기")
    @RequestMapping(value = "/post/{p_id}", method = RequestMethod.DELETE)
    public Mono<ResponseEntity<Void>> deleteThePost(@PathVariable(value = "p_id") String id) {
        return this.postRepository.findById(id)
                .flatMap(i -> postRepository.delete(i)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
