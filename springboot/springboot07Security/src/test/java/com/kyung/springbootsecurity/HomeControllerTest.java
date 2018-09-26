package com.kyung.springbootsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
//    @WithMockUser
    public void hello() throws Exception {
        mockMvc.perform(get("/hello")
                    .accept(MediaType.TEXT_HTML)) // 이를 사용하면 spring-security 가 제공하는 로그인 인증으로 처리된다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void hello_without_user() throws Exception {
        mockMvc.perform(get("/hello")
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isUnauthorized()); // mock 이 없으면 unauthorized 하도록 처리
    }

    // 아래는 아무것도 Accept Header 가 없으므로 Basic Auth 에 관한 인증이 뜨게 된다.
    @Test
//    @WithMockUser
    public void my() throws Exception {
        mockMvc.perform(get("/my"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("my"));
    }
}