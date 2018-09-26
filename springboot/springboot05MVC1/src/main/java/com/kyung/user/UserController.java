package com.kyung.user;

import org.springframework.web.bind.annotation.*;

/* HttpMessageConverter
    - Http 요청 본문을 객체로 변경하거나, 객체를 HTTP 응답 본문으로 변경할 때 사용
    - @RequestBody : 응답 요청
    - @ResponseBody : 응답 본문

    단, RestController 인 경우 @ResponseBody 는 생략해도 된다. (hello() 메서드는 이를 생략한 것)
*/

@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/users/create")
    public @ResponseBody User create(@RequestBody User user) {
        return user;
    }

}
