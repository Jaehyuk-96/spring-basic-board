package com.springbasic.board.service;

import com.springbasic.board.domain.CommentDTO;
import com.springbasic.board.mapper.BoardMapper;
import com.springbasic.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;

    public void addComment(CommentDTO commentDTO) {
        commentMapper.addComment(commentDTO);
    }

    public List<CommentDTO> getList(int cno) {
        return commentMapper.getList(cno);

    }

    public int getTotal(int bno) {
        return commentMapper.getTotal(bno);
    }

    public void deleteComment(CommentDTO commentDTO) {
        commentMapper.deleteComment(commentDTO);
    }
}
