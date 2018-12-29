package com.kyung.boardex.controller;

import com.kyung.boardex.annotation.SocialUser;
import com.kyung.boardex.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/{facebook|google|kakao}/complete") // 인증이 성공적으로 처리된 이후 리다이렉트되는 경로
    public String loginComplete(@SocialUser User user) {
        return "redirect:/board/list";
    }
}
