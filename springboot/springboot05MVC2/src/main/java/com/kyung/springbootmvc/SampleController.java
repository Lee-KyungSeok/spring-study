package com.kyung.springbootmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    // view 의 이름을 리턴하여 찾는다.
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "kyungseok");
        return "hello";
    }

}
