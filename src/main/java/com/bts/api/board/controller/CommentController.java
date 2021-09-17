package com.bts.api.board.controller;

import com.bts.api.board.domain.Comment;
import com.bts.api.board.repository.CommentRepository;
import com.bts.api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @RequestMapping(value = "/board/{id}", method = RequestMethod.POST)
    public Mono<Comment> postTheComment(@PathVariable("id") String id, @RequestBody Comment comment) {
        return postRepository.findById(id).then(commentRepository.save(comment));
    }

}
