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
import com.itu.checkin.model.entity.EntityBase;
import com.itu.checkin.model.entity.user.feedback.Feedback;
import com.itu.checkin.service.serviceinterface.FeedbackService;
import com.itu.checkin.service.serviceinterface.PlaceService;

@Controller
public class FeedbackController extends AbstractController {
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private PlaceService placeService;

	@RequestMapping(value = "/nativeapp/feedback", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountFeedbackSave(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "placeId") Integer placeId,
			@RequestParam(required = true, value = "ambiance") Short ambiance,
			@RequestParam(required = true, value = "price") Short price,
			@RequestParam(required = true, value = "service") Short service,
			@RequestParam(required = true, value = "flavour") Short flavour)
			throws ParseException {

		Feedback feedback = new Feedback();
		feedback.setAmbiance(ambiance);
		feedback.setEntityBase(new EntityBase());
		feedback.setFlavour(flavour);
		feedback.setPlace(placeService.findById(placeId).getResult());
		feedback.setPrice(price);
		feedback.setService(service);
		feedback.setUserIndividual(getCurrentUser(httpSession));
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(feedbackService.save(feedback));

	}
}
