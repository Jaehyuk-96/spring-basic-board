package com.springbasic.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long cno;
    private String commentWriter;
    private String commentContent;
    private int commentLike;
    private int commentHate;
    private String reCom;
    private Long bno;
    private Timestamp commentCreatedTime;
}
