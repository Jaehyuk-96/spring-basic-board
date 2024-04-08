package com.springbasic.board.mapper;

import com.springbasic.board.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    BoardDTO read(Integer bno);

    void increaseViewCount(Integer bno);

    int write(BoardDTO boardDTO);


    List<BoardDTO> pagingList(Map<String, Integer> pagingParams);

    int boardCount();

    int delete(Map map);

    int update(BoardDTO boardDTO);
}
