package com.example.seed.controller;

import com.example.seed.service.UserService;
import com.example.seed.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/seed")
    public String test() {
        List<UserVo> user = userService.findAll();
        return user.toString();
    }
}
