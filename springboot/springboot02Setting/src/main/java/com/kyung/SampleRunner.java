package com.kyung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    /*
    @Value("${kyung.name}")
    private String name;

    @Value("${kyung.age}")
    private int age;
    */

    @Autowired
    private String hello;

    @Autowired
    KyungProperties kyungProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("====================");
        System.out.println(kyungProperties.getName());
        System.out.println(kyungProperties.getAge());
        System.out.println(hello);
        System.out.println("====================");
    }
}
