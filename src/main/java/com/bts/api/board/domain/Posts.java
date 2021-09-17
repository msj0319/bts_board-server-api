package com.bts.api.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "posts")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Posts implements Serializable {
    //게시물을 누르면 보여질 부분
    private @Id
    String id;
    private String author;
    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime createPostDate = LocalDateTime.now();

    @Builder
    public Posts(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
