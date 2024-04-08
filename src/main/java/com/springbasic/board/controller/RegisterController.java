package com.springbasic.board.controller;

import com.springbasic.board.domain.UserDTO;
import com.springbasic.board.mapper.RegisterMapper;
import com.springbasic.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterMapper registerMapper;

    @GetMapping("/add")
    public String addForm() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute UserDTO userDTO){
        registerMapper.add(userDTO);
        return "loginForm";
    }

}
