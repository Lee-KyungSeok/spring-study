package com.kyung.springbootmvc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

//    @Autowired
//    MockMvc mockMvc;

    // HtmlUnit 을 사용하면 WebClient 를 가져올 수 있다.
    @Autowired
    WebClient webClient;

    @Test
    public void hello() throws Exception {
        /* 요청
            - "/hello"
           응답
            - 모델 name : kyungseok
            - 뷰 name : hello
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print()) // JSP 로 사용하면 이것처럼 찍는 것은 힘들다.
                .andExpect(view().name("hello"))
                .andExpect(model().attribute("name", is("kyungseok")))
                .andExpect(xpath("//h1").string("kyungseok")) // html 태그를 가져올 수 있다.
                .andExpect(content().string(containsString("kyungseok")));
        */

        // htmlunit 을 이용하면 page 를 가져와서 테스트 하 수 있다.
        // 물론 mockMVC 를 이용하여 model 등을 테스트 할 수 있다.
        HtmlPage page = webClient.getPage("/hello");
        HtmlHeading1 h1 = page.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("kyungseok");
    }
}