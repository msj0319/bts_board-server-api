package com.bts.api.board.repository;

import com.bts.api.board.domain.Posts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends ReactiveMongoRepository<Posts, String> {
}
