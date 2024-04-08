package com.springbasic.board.controller;

import com.springbasic.board.domain.BoardDTO;
import com.springbasic.board.domain.PageDTO;
import com.springbasic.board.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("mode", "new");
        return "board";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute BoardDTO boardDTO, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        try {
            boardService.write(boardDTO, session);
            redirectAttributes.addFlashAttribute("msg", "WRT_OK");//성공적으로 등록 msg
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(boardDTO);
            redirectAttributes.addFlashAttribute("mode", "new");//글쓰기 mode
            redirectAttributes.addFlashAttribute("msg", "WRT_ERR");//쓰기 에러 msg
        }
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO,  RedirectAttributes rattr, Model model, HttpSession session){

        try {
            if (boardService.modify(boardDTO, session)!= 1)
                throw new Exception("Modify failed.");

            rattr.addFlashAttribute("msg", "MOD_OK");
            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(boardDTO);
            model.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("bno") Integer bno,  RedirectAttributes rattr, HttpSession session) throws Exception {
            int deleteFlag = boardService.delete(bno, session);
            rattr.addFlashAttribute("msg", "DEL_OK");//성공적으로 등록 msg
        try {
            if(deleteFlag!=1)
                throw new Exception("Delete failed.");

        }catch (Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
            return "redirect:/board/list";

        }
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model, HttpServletRequest request){
        if(!loginCheck(request))//logincheck가 false를 반환하면 다시 로그인 화면으로 이동(login안했을시)
            return "redirect:/login/login?toURL="+request.getRequestURL();


        List<BoardDTO> pagingList = boardService.pageList(page);
        PageDTO pageDTO = boardService.pagingParam(page);
        model.addAttribute("boardList", pagingList);
        model.addAttribute("paging", pageDTO);
        return "boardList";

    }


    @GetMapping("/read")
    public String read(Integer bno,RedirectAttributes rattr, Model model, HttpSession session){
        try{//예외처리
            BoardDTO boardDTO = boardService.read(bno);
            model.addAttribute("boardDto", boardDTO);
        }catch (Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "READ_ERR");//예외 발생시 read_err 발생
            return "redirect:/board/list";

        }
        System.out.println(session.getAttribute("id"));

        return "board";
    }

    //로그인체크 메서드
    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();//세션을가져옴
        return session.getAttribute("id")!=null;//세션의 id가 비어있지 않으면 리턴함
    }
}
