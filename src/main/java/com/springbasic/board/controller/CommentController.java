package com.springbasic.board.controller;


import com.springbasic.board.domain.CommentDTO;
import com.springbasic.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(@RequestBody CommentDTO commentDTO, HttpSession session){
        if(session.getAttribute("id")==null){
            return "fail";
        }else {
            commentService.addComment(commentDTO);
            return "success";
        }
    }

    @DeleteMapping("/deleteComment")
    public String deleteComment(@RequestBody CommentDTO commentDTO, HttpSession session){
      if(session.getAttribute("id")==null){
          return "fail";
      }else {
          commentService.deleteComment(commentDTO);
          return "success";
      }
    }

    @GetMapping("/commentList/{bno}")
    public Map<String, Object> getList(@PathVariable int bno, Model model){
        List<CommentDTO> list = commentService.getList(bno);
        int total = commentService.getTotal(bno);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }
}