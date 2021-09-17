package com.bts.api.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private @Id String id;
    private String comment_writer;
    private String comment_content;
    @CreatedDate
    private LocalDateTime createPostDate = LocalDateTime.now();
}
