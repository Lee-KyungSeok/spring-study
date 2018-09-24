package mall.controller;

import mall.Mall;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/mall")
public class MallController {

    @ModelAttribute("cp")
    public String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    @ModelAttribute("serverTime")
    public String getServerTime(Locale locale) {

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        return dateFormat.format(date);
    }

    // 생성된 쿠키를 가져올 수 있다. (단, 쿠키는 없을수도 있으므로 required 를 false 로 지정한다.
    // 쿠키는 @CookieValue 를 통해서 명시한다.
    @RequestMapping("/index")
    public String mallIndex(Mall mall,
                            @CookieValue(value="gender", required=false) Cookie genderCookie,
                            HttpServletRequest request) {

        if(genderCookie != null)
            // 쿠키에서 값을 가져온다.
            mall.setGender(genderCookie.getValue());

        return "/mall/index";
    }

    @RequestMapping("/main")
    public String mallMain(Mall mall, HttpServletResponse response){

        // 쿠키를 생성한다.
        Cookie genderCookie = new Cookie("gender", mall.getGender());

        if(mall.isCookieDel()) {
            genderCookie.setMaxAge(0);
            mall.setGender(null);
        } else {
            // 쿠키의 유지 기간을 설정한다.
            genderCookie.setMaxAge(60*60*24*30);
        }
        // HttpServletResponse 에 쿠키를 저장한다.
        response.addCookie(genderCookie);

        return "/mall/main";
    }

}
