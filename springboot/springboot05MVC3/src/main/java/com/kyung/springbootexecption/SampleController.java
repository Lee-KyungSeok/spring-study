package com.kyung.springbootexecption;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@ControllerAdvice // 여러 군데에서 전역으로 에러 발생을 컨트롤하도록 설정할 수 있다.
public class SampleController {

    // Spring 에서 에러 처리
    @GetMapping("/hello")
    public String hello() {
        throw new SampleException();
    }

    @ExceptionHandler(SampleException.class)
    public @ResponseBody AppError sampleError(SampleException e) {
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("Unkown");
        return appError;
    }
}

/* 스프링 부트에서 에러는 BasicErrorController 에서 기본적으로 제공하며
    - 커스터마이징하고 싶다면 위를 참고하여 ErrorController 를 상속받아 구현해주면 된다.

   그러나 static 다랙토리에서 정적 페이지를 만들어서 보여줄 수 있다. (서버단에서는 단순히 이정도만 보내주곤 한다.)
 */