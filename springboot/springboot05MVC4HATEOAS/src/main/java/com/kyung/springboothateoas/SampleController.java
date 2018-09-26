package com.kyung.springboothateoas;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public Hello hello() {
        Hello hello = new Hello();
        hello.setPrefix("Hey, ");
        hello.setName("Kyungseok");

        // 링크정보 추가 (여러 방법이 있지만 이는 그 중 하나)
        Resource<Hello> helloResource = new Resource<>(hello);
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());
        return hello;
    }
}
