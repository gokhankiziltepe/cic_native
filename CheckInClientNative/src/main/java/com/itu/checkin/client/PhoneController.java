package com.itu.checkin.client;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.itu.checkin.service.serviceinterface.PhoneService;

@Controller
public class PhoneController extends AbstractController {
	@Autowired
	private PhoneService phoneService;

	@RequestMapping(value = "/nativeapp/phone/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetPhone(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "placeId") Integer placeId)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(phoneService.findByOwnerEntityId(placeId));

	}
}
