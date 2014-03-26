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
import com.itu.checkin.service.serviceinterface.PlaceService;

@Controller
public class PlaceController extends AbstractController {
	@Autowired
	private PlaceService placeService;

	@RequestMapping(value = "/nativeapp/place/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetPhoto(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(placeService.findById(id));

	}
}
