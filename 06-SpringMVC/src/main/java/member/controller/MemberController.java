package member.controller;

import member.Member;
import member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/member") // 공통된 부분은 클래스에서 매핑할 수 있다.
public class MemberController {

//	@Autowired
    @Resource(name="memService") // 이를 이용해서 서비스를 주입시킬 수 있다.
    MemberService service;

    // ModelAttribute 를 이용하면 view 에서 지정한 이름으로 바로 사용할 수 있다.
    @ModelAttribute("serverTime")
    public String getServerTime(Locale locale) {

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        return dateFormat.format(date);
    }

    // Post 방식이면 메서드를 명시해주고 Get 이면 명시할 필요는 없다(default)
    @RequestMapping(value="/memJoin", method=RequestMethod.POST)
    public String memJoin(Model model, HttpServletRequest request) {

        // body 에 넘어온 값은 getParameter 를 통해서 가져올 수 있다.
        String memId = request.getParameter("memId");
        String memPw = request.getParameter("memPw");
        String memMail = request.getParameter("memMail");
        String memPhone1 = request.getParameter("memPhone1");
        String memPhone2 = request.getParameter("memPhone2");
        String memPhone3 = request.getParameter("memPhone3");

        service.memberRegister(memId, memPw, memMail, memPhone1, memPhone2, memPhone3);

        model.addAttribute("memId", memId);
        model.addAttribute("memPw", memPw);
        model.addAttribute("memMail", memMail);
        model.addAttribute("memPhone", memPhone1 + " - " + memPhone2 + " - " + memPhone3);

        return "memJoinOk";
    }

    // Command 객체의 setter 와 getter 를 통해서 간단하게 구현 가능하다.
    // - view 에서도 member command 객체를 이용하게 되므로 model 도 필요 없게 된다.
    // - jsp 에서 member.memId 와 같이 바꿔주면 된다.
    // - ModelAttribute 를 이용해서 커맨드 객체의 이름을 변경할 수 있다. (jsp 에서 mem.memId 와 같이 사용하게 된다.)
    @RequestMapping(value="/memJoin2", method=RequestMethod.POST)
    public String memJoin2(@ModelAttribute("mem") Member member) {

        service.memberRegister(member.getMemId(), member.getMemPw(), member.getMemMail(),
                member.getMemPhone1(), member.getMemPhone2(), member.getMemPhone3());

        return "memJoinOk2";
    }

    // RequestParam 을 통해서 body 값을 바로 가져올 수 있다.
    // - required 를 통해 필수값이 아니도록 선택할 수 있다.
    // - defaultValue 를 통해 값이 없는 경우의 default 값을 선택할 수 있다.
    @RequestMapping(value="/memLogin", method=RequestMethod.POST)
    public String memLogin(Model model,
                           @RequestParam(value="memId", required = false, defaultValue = "asdf") String memId,
                           @RequestParam("memPw") String memPw) {

        // String memId = request.getParameter("memId");
        // String memPw = request.getParameter("memPw");

        Member member = service.memberSearch(memId, memPw);

        try {
            model.addAttribute("memId", member.getMemId());
            model.addAttribute("memPw", member.getMemPw());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "memLoginOk";
    }

    @RequestMapping(value = "/memRemove", method = RequestMethod.POST)
    public String memRemove(@ModelAttribute("mem") Member member) {

        service.memberRemove(member);

        return "memRemoveOk";
    }

	/*
	@RequestMapping(value = "/memModify", method = RequestMethod.POST)
	public String memModify(Model model, Member member) {

		Member[] members = service.memberModify(member);

		model.addAttribute("memBef", members[0]);
		model.addAttribute("memAft", members[1]);

		return "memModifyOk";
	}
	*/

	// ModelAndView 를 이용하여 데이터와 뷰를 한번에 넘길 수 있다.
    @RequestMapping(value = "/memModify", method = RequestMethod.POST)
    public ModelAndView memModify(Member member) {

        Member[] members = service.memberModify(member);

        ModelAndView mav = new ModelAndView();
        // 모델에 값을 넣을 수 있다.
        mav.addObject("memBef", members[0]);
        mav.addObject("memAft", members[1]);

        // 뷰를 지정할 수 있다.
        mav.setViewName("memModifyOk");

        return mav;
    }
}
