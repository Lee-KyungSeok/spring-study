package com.kyung;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class SampleArgument {

    public SampleArgument(ApplicationArguments arguments) {
        System.out.println("foo: " + arguments.containsOption("foo"));
        System.out.println("bar: " + arguments.containsOption("bar"));
    }
}

/* Configuration 세팅에서 VM options 은 argument 로 들어오지 않고 Program arguments 는 들어온다.
*/