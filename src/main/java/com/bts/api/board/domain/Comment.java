package com.bts.api.board.domain;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    @ApiParam(value = "댓글 id", required = true)
    private @Id String c_id;
    @ApiParam(value = "댓글 작성자", required = true)
    private String comment_writer;
    @ApiParam(value = "댓글 내용", required = true)
    private String comment_content;
    @ApiParam(value = "댓글 생성 시간", required = true)
    @CreatedDate
    private LocalDateTime createPostDate = LocalDateTime.now();
    @ApiParam(value = "댓글 수정 시간", required = true)
    @LastModifiedDate
    private LocalDateTime modifiedPostDate = LocalDateTime.now();

    @Builder
    public Comment(@NonNull String comment_writer, @NonNull String comment_content) {
        this.comment_writer = comment_writer;
        this.comment_content = comment_content;
    }
}