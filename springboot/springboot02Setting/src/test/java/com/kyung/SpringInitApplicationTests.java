package com.kyung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:/test.properties") // 이것이 우선순위가 더 높다.
@SpringBootTest()
public class SpringInitApplicationTests {

    @Autowired
    Environment environment;

    @Test
    public void contextLoads() {
        environment.getProperty("kyung.name");
    }
}
