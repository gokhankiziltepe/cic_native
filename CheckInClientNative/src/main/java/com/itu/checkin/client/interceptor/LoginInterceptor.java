package com.itu.checkin.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_CORPORATE = "ROLE_CORPORATE";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getServletPath().startsWith("/hesabim")) {
			if (request.isUserInRole(ROLE_ADMIN)
					|| request.isUserInRole(ROLE_CORPORATE)) {
				response.sendRedirect(request.getContextPath()
						+ "/nativeapp/401");
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
