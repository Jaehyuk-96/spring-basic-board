package com.springbasic.board.mapper;

import com.springbasic.board.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void addComment(CommentDTO commentDTO);

    List<CommentDTO> getList(int cno);

    int getTotal(int bno);

    void deleteComment(CommentDTO commentDTO);
}
