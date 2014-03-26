package com.itu.checkin.client.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.itu.checkin.service.exception.CustomGenericException;

@Component
public class CheckInExceptionResolver extends ExceptionHandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object o, Exception ex) {

		CustomGenericException exception = null;
		if (ex instanceof CustomGenericException) {

			exception = (CustomGenericException) ex;
		} else if (ex instanceof Exception) {

			exception = new CustomGenericException("UnknownException",
					"Bilinmeyen bir hata oluştu.");
		}
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/nativeapp/error");
		response.setStatus(500);
		request.setAttribute("currentException", exception);
		request.getSession().setAttribute("currentException", exception);
		return modelAndView;
	}
}
