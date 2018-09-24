package com.bs.lec21.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 인터셉터는 HandlerInterceptorAdapter 를 상속받아 사용한다.
public class MemberLoginInterceptor extends HandlerInterceptorAdapter {

	// 컨트롤러가 작동하기 전에 먼저 실행한다. (가장 많이 사용된다.)
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 세션에 따라서 리다이렉트 시키는 코드!
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object obj = session.getAttribute("member");
			if(obj != null) 
				return true;
		}
		
		response.sendRedirect(request.getContextPath() + "/");
		return false;
	}
	
	// 컨트롤러가 작동된 후에 실행한다.
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	// view 까지 모두 작업한 후에 실행한다.
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
	}
}