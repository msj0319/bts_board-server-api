package com.bts.api.board.domain;

import io.swagger.annotations.ApiParam;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Document(value = "posts")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Posts implements Serializable {
    @ApiParam(value = "id", required = true)
    private @Id
    String p_id;
    @ApiParam(value = "작성자", required = true)
    private String author;
    @ApiParam(value = "제목", required = true)
    private String title;
    @ApiParam(value = "내용", required = true)
    private String content;
    @ApiParam(value = "게시물 생성 시간", required = true)
    @CreatedDate
    private LocalDateTime createPostDate = LocalDateTime.now();
    @ApiParam(value = "게시물 수정 시간", required = true)
    @LastModifiedDate
    private LocalDateTime modifiedPostDate = LocalDateTime.now();
    @ApiParam(value = "조회수", required = true)
    private int view_cnt;

    private ArrayList<Comment> commentList = new ArrayList<>();

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList, Comment comment) {
        comment.setComment_writer(comment.getComment_writer());
        comment.setComment_content(comment.getComment_content());
        commentList.add(comment);
    }

    @Builder
    public Posts(@NonNull String author, @NonNull String title, @NonNull String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
