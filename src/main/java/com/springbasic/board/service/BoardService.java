package com.springbasic.board.service;

import com.springbasic.board.domain.BoardDTO;
import com.springbasic.board.domain.PageDTO;
import com.springbasic.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    public BoardDTO read(Integer bno) throws Exception{
        boardMapper.increaseViewCount(bno);//조회수 증가
        return boardMapper.read(bno);
    }

    public void write(BoardDTO boardDTO, HttpSession session) throws Exception{
        String writer = (String) session.getAttribute("id");
        boardDTO.setWriter(writer);

        try {
            if (boardMapper.write(boardDTO) != 1)
                throw new Exception("Write failed.");
        } catch (Exception e) {
            throw new Exception("Write failed.", e);
        }
    }

    public int delete(Integer bno, HttpSession session) throws Exception{
        String writer = (String) session.getAttribute("id");
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return boardMapper.delete(map);

    }





    int pageLimit = 10;

    int blockLimit = 5;

    public List<BoardDTO> pageList(int page) {
        int pageStart = (page-1)*pageLimit;
        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start",pageStart);
        pagingParams.put("limit", pageLimit);
        List<BoardDTO> pagingList = boardMapper.pagingList(pagingParams);

        return pagingList;
    }

    public PageDTO pagingParam(int page) {
        int boardCount = boardMapper.boardCount();
        int maxPage = (int)(Math.ceil((double) boardCount / pageLimit));
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }


    public int modify(BoardDTO boardDTO, HttpSession session) {
        String writer = (String) session.getAttribute("id");
        boardDTO.setWriter(writer);
        return boardMapper.update(boardDTO);


    }
}


