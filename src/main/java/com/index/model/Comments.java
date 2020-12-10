package com.index.model;

import com.index.dto.AddCommentDto;
import com.index.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "comments", schema="gradebook")
@Entity
@Builder
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    long commentId;
    long behaviorId;
    long userId;
    String comment;
    String date;

    public static Comments createComment(AddCommentDto addCommentDto) {
        return Comments.builder()
                .behaviorId(addCommentDto.getBehaviorId())
                .comment(addCommentDto.getComment())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now()))
                .build();
    }

    public CommentDto dto() {
        return CommentDto.builder()
                .behaviorId(behaviorId)
                .commentId(commentId)
                .comment(comment)
                .date(date)
                .userId(userId)
                .build();
    }
}
