package com.itu.checkin.client.error;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.itu.checkin.service.exception.CustomGenericException;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class ErrorController {
	@RequestMapping(value = "/nativeapp/404", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String handle404() {
		Gson gson = new Gson();
		ServiceResult<Object> serviceResult = new ServiceResult<Object>(
				ServiceResultConstant.FAIL_ON_PAGE, "Sayfa bulunamadı", null);
		return gson.toJson(serviceResult);
	}

	@RequestMapping(value = "/nativeapp/401", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String handle401() {
		Gson gson = new Gson();
		ServiceResult<Object> serviceResult = new ServiceResult<Object>(
				ServiceResultConstant.FAIL_ON_AUTHORITY, "Yetkisiz erişim",
				null);
		return gson.toJson(serviceResult);
	}

	@RequestMapping(value = "/nativeapp/error", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String handleError(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws UnsupportedEncodingException {
		Gson gson = new Gson();
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletRequest.setCharacterEncoding("UTF-8");
		CustomGenericException customGenericException = (CustomGenericException) httpServletRequest
				.getSession().getAttribute("currentException");
		if (customGenericException == null) {
			customGenericException = (CustomGenericException) httpServletRequest
					.getAttribute("currentException");
		}
		ServiceResult<Object> serviceResult;
		if (customGenericException == null) {
			serviceResult = new ServiceResult<Object>(
					ServiceResultConstant.FAIL, "Bilinmeyen bir hata oluştu",
					null);
		} else {
			serviceResult = new ServiceResult<Object>(
					ServiceResultConstant.FAIL,
					customGenericException.getErrorMessage(), null);
		}
		return gson.toJson(serviceResult);
	}
}
