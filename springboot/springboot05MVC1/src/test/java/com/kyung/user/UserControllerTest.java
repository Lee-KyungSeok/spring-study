package com.kyung.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"kyungseok\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")        // post 요청
                .contentType(MediaType.APPLICATION_JSON_UTF8)   // contentType 을 medisType의 json 형식으로
                .accept(MediaType.APPLICATION_JSON_UTF8)        // 응답으로 Json 을 원한다고 요청
                .content(userJson))                             // 응답 본문에는 userJson 을 넣는다.
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", is(equalTo("kyungseok"))))    //Json 본문에 username 이 kyungseok 이길 바란다.
                    .andExpect(jsonPath("$.password", is(equalTo("123"))));
    }

    @Test
    public void createUser_XML() throws Exception {
        String userJson = "{\"username\":\"kyungseok\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_XML) // 뷰리졸버를 제공하므로 HttpMessageConvertersAutoConfiguration 에서 알아서 처리 해준다.(디펜던시에만 XML 컨버터를 추가해주면)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("kyungseok"))
                .andExpect(xpath("/User/password").string("123"));
    }
}
