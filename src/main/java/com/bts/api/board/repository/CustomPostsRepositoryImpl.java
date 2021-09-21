package com.bts.api.board.repository;

import com.bts.api.board.domain.Comment;
import com.bts.api.board.domain.Posts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class CustomPostsRepositoryImpl implements CustomPostsRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    //조회수 증가 쿼리
    @Override
    public Mono<Posts> viewPostAndUpdateViewCount(String id, int cnt) {
        Query query = new Query(Criteria.where("p_id").is(id));
        Update update = new Update().set("view_cnt", cnt);
        return mongoTemplate.findAndModify(query, update, Posts.class);
    }

    //댓글 작성 -> Comment Repository 댓글 저장 -> 댓글 내용 get, PostsRepository 에 삽입하는 쿼리
//    public Mono<Posts> removeComment(String p_id, String c_id) {
//        Query query = Query.query(Criteria.where("p_id").is(p_id)
//                .and("commentList.c_id").is(c_id));
////        Update update = new Update();
////        update.unset("commentList.$");
//        return mongoTemplate.findAndRemove(query, Posts.class);
//    }
}
