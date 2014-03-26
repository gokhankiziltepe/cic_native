package com.itu.checkin.client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import com.itu.checkin.model.entity.EntityBase;
import com.itu.checkin.model.entity.place.placecheckin.CheckIn;
import com.itu.checkin.service.serviceinterface.CheckInService;
import com.itu.checkin.service.serviceinterface.PlaceService;
import com.itu.checkin.service.serviceresult.ServiceResult;

@Controller
public class CheckInController extends AbstractController {
	@Autowired
	private CheckInService checkInService;

	@Autowired
	private PlaceService placeService;

	@RequestMapping(value = "/nativeapp/checkin/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountGetCheckInGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws ParseException {
		ServiceResult<List<CheckIn>> serviceResult = checkInService
				.findByUserIndividualId(getCurrentUser(httpSession).getId());
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(serviceResult);

	}

	@RequestMapping(value = "/nativeapp/checkin", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountGetCheckIn(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "placeId") Integer placeId)
			throws ParseException {

		CheckIn checkIn = new CheckIn();
		checkIn.setDateAdded(new Timestamp(new Date().getTime()));
		checkIn.setEntityBase(new EntityBase());
		checkIn.setPlace(placeService.findById(placeId).getResult());
		checkIn.setUserIndividual(getCurrentUser(httpSession));
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(checkInService.save(checkIn));

	}

	@RequestMapping(value = "/nativeapp/checkin/delete", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountCheckInDelete(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpSession httpSession,
			@RequestParam(required = true, value = "checkInId") Integer checkInId)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(checkInService.delete(checkInId));

	}
}
