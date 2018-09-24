package member.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import member.Member;
import member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/* 세션 주요 메소드 및 플로어
	- getId() : 세션 ID 를 반환
	- setAttribute() : 세션 객체에 속성을 저장
	- getAttribute() : 세션 객체에 저장된 속성을 반환
	- removeAttribute() : 세션 객체에 저장된 속성을 제거
	- setMaxInactiveInterval() : 세션 객체의 유지시간 설정
	- getMaxInactiveInterval() : 세션 객체의 유지시간 반환
	- invalidate() : 세션 객체의 모든 정보를 삭제
 */

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService service;
	
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
	
	// Join
	@RequestMapping("/joinForm")
	public String joinForm(Member member) {
		return "/member/joinForm";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinReg(Member member) {
		
		service.memberRegister(member);
		
		return "/member/joinOk";
	}
	
	// Login
	@RequestMapping("/loginForm")
	public String loginForm(Member member) {
		return "/member/loginForm";
	}


	// HttpServletRequest 에서 세션을 가져와서 처리한다.
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String memLogin(Member member, HttpServletRequest request) {
		
		Member mem = service.memberSearch(member);

		// HttpServletRequest 에서 세션을 가져온다.
		HttpSession session = request.getSession();
		// 세션 속성을 지정하고 저장한다.
		session.setAttribute("member", mem);
		
		return "/member/loginOk";
	}


	// 아래처럼 세션을 인자로 넣어 바로 받아올 수 있다.
	@RequestMapping("/logout")
	public String memLogout(Member member, HttpSession session) {

		// invalidate 하면 세션이 삭제된다.
		session.invalidate();
		
		return "/member/logoutOk";
	}
	
	// Modify
	@RequestMapping(value = "/modifyForm", method = RequestMethod.GET)
	public ModelAndView modifyForm(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("member", service.memberSearch(member));
		
		mav.setViewName("/member/modifyForm");
		
		return mav;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ModelAndView modify(Member member, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Member mem = service.memberModify(member);
		session.setAttribute("member", mem);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("memAft", mem);
		mav.setViewName("/member/modifyOk");
		
		return mav;
	}
	
	// Remove
	@RequestMapping("/removeForm")
	public ModelAndView removeForm(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		HttpSession session =  request.getSession();
		Member member = (Member) session.getAttribute("member");
		mav.addObject("member", member);
		mav.setViewName("/member/removeForm");
		
		return mav;
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String memRemove(Member member, HttpServletRequest request) {
		
		service.memberRemove(member);
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "/member/removeOk";
	}
	
}
