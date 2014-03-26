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
import com.itu.checkin.service.serviceinterface.PhotoService;

@Controller
public class PhotoController extends AbstractController {
	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "/nativeapp/photo/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetPhoto(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(photoService.findById(id));

	}

	@RequestMapping(value = "/nativeapp/user/photos", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetCurrentUserPhoto(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws ParseException {

		return new GsonBuilder()
				.setDateFormat("dd/MM/yyyy hh:mm")
				.create()
				.toJson(photoService.findByOwnerUserBaseId(getCurrentUser(
						httpSession).getId()));

	}

	@RequestMapping(value = "/nativeapp/photo/entity/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountPhotoEntityGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpSession httpSession,
			@RequestParam(required = true, value = "entityBaseId") Integer entityBaseId)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(photoService.findByEntityBaseId(entityBaseId));

	}
}
