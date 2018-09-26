package com.kyung.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* SpringBootTest 어노테이션은 Application 부터 시작하는 통합테스트이므로 큰 테스트로 사용된다.
   레이어별로 잘라서 사용하고 싶다면 아래 어노테이션으로 사용한다.
   - @JsonTest
   - @WebMvcTest (WebMvc Test 만 이용)
   - @WebFluxTest
   - @DataJpaTest

   OutputCapture
   - 로그를 비롯해서 콘솔에 찍히는 것을 모두 출력한다.
*/

/* 1. Mock MVC 사용 방법
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 서블릿을 mocking 한 것으로 설정
@AutoConfigureMockMvc // MockMvc 를 사용할 수 있게 해준다.
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello kyungseok"))
                .andDo(print());

    }
}
*/

/* 2. 실제 Test 용 Servlet 을 사용 - TestRestTemplate 이용
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 실제 서블릿을 사용하게 된다.
public class SampleControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    // SampleService Bean 을 MockBean 으로 교체하여 사용하게 된다.
    // Controller 만 테스트하고 싶은 경우 사용한다.
    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        // Mocking 한 것을 사용
        when(mockSampleService.getName()).thenReturn("kyungseok"); // getName 이 호출되면 kyungseok 을 호출

        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello kyungseok");
    }
}
*/

/* 3. 실제 Test 용 Servlet 을 사용 - webTestClient 이용(비동기로 작동한다.)
//      - 이는 spring-boot-starter-webflux 를 디펜던시에 추가되어야 사용 가능하다.
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 실제 서블릿을 사용하게 된다.
public class SampleControllerTest {

    @Autowired
    WebTestClient webTestClient;

    // SampleService Bean 을 MockBean 으로 교체하여 사용하게 된다.
    // Controller 만 테스트하고 싶은 경우 사용한다.
    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        // Mocking 한 것을 사용
        when(mockSampleService.getName()).thenReturn("kyungseok"); // getName 이 호출되면 kyungseok 을 호출

        webTestClient.get().uri("/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello kyungseok");
    }
}
*/

// 4. 각 레이어 별로 테스트 사용하고 싶은 경우 (Controller 하나만 테스트하게 된다)
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    // Rule 은 Junit 을 Wrapping 하고 있으며 outputCapture 를 이용할 수 있다.
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @MockBean // 사용되는 Bean 은 주입시켜줘야 한다.
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("kyungseok");

        mockMvc.perform(get("/hello"))
                .andExpect(content().string("hello kyungseok"));

        // 로그 등을 확인할 수 있다.
        assertThat(outputCapture.toString())
                .contains("Cory")
                .contains("xxx");
    }
}