package com.kyung.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SampleService sampleService;

    @GetMapping("/hello")
    public String hello() {
        logger.info("Cory");
        System.out.println("이렇게는 하지 말것 ㅎㅎㅎ 하지만 테스트를 위해");
        return "hello" + sampleService.getName();
    }
}
