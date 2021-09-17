package com.bts.api.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "boards")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Board implements Serializable {
    //board url 요청 시 보여질 내용
    private @Id String id;
    private String title;
    private String author;
    @CreatedDate
    private LocalDateTime createPostDate;

    @Builder
    public Board(String title, String author, LocalDateTime createPostDate) {
        this.title = title;
        this.author = author;
        this.createPostDate = createPostDate;
    }
}
