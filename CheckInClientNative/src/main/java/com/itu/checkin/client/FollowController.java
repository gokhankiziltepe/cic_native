package com.itu.checkin.client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

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
import com.itu.checkin.model.entity.user.follow.Follow;
import com.itu.checkin.service.serviceinterface.EntityBaseService;
import com.itu.checkin.service.serviceinterface.FollowService;

@Controller
public class FollowController extends AbstractController {
	@Autowired
	private FollowService followService;
	@Autowired
	private EntityBaseService entityBaseService;

	@RequestMapping(value = "/nativeapp/follow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetPhone(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "placeId") Integer placeId)
			throws ParseException {
		Follow follow = new Follow();
		follow.setApproved(true);
		follow.setDateAdded(new Timestamp(new Date().getTime()));
		follow.setEntityBase(new EntityBase());
		follow.setFollowedEntityBase(entityBaseService.findById(placeId)
				.getResult());
		follow.setFollowerUserIndividual(getCurrentUser(httpSession));

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(followService.save(follow));

	}
}
