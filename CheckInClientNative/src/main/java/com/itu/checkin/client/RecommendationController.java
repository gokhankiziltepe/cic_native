package com.itu.checkin.client;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.itu.checkin.model.entity.user.type.UserIndividual;
import com.itu.checkin.recommendation.classifier.ClassifyStrategy;
import com.itu.checkin.recommendation.model.Recommendation;
import com.itu.checkin.service.serviceinterface.UserIndividualService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class RecommendationController {

	@Autowired
	private ClassifyStrategy classifier;

	@Autowired
	private UserIndividualService userIndividualService;
	
	@RequestMapping(value = "/nativeapp/getRecommendations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	@ResponseBody
	public String getRecommendations(HttpSession httpSession) throws Exception {
		UserIndividual currentUser = getCurrentUser(httpSession);
		List<Recommendation> recommendations = classifier.getRecommendations(currentUser);
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create().toJson(recommendations);
	}
	
	@RequestMapping(value = "/nativeapp/getRecommendationsForGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	@ResponseBody
	public String getRecommendations(@RequestBody List<Integer> userIds) throws Exception {
		ServiceResult<List<UserIndividual>> serviceResult = userIndividualService.findUsersByIds(userIds);
		
		if (!serviceResult.isSuccess()) {
			//TODO throw sth
		}
		
		List<Recommendation> recommendations = classifier.getGroupRecommendations(serviceResult.getResult());
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create().toJson(recommendations);
	}

	private UserIndividual getCurrentUser(HttpSession httpSession) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		UserIndividual userIndividual = (UserIndividual) httpSession
				.getAttribute("user");
		if (httpSession.getAttribute("user") == null) {
			ServiceResult<UserIndividual> serviceResult = userIndividualService
					.findByEmail(auth.getName());
			if (serviceResult.getCodeConstant() == ServiceResultConstant.SUCCESS) {
				httpSession.setAttribute("user", serviceResult.getResult());
				return serviceResult.getResult();
			} else {
				return null;
			}

		} else {
			return userIndividual;
		}
	}
}
