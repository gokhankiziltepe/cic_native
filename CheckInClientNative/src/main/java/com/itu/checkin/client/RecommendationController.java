package com.itu.checkin.client;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.itu.checkin.model.entity.user.type.UserIndividual;
import com.itu.checkin.recommendation.classifier.ClassifyStrategy;
import com.itu.checkin.recommendation.model.Recommendation;

@Controller
public class RecommendationController extends AbstractController {

	@Autowired
	private ClassifyStrategy classifier;

	@RequestMapping(value = "/hesabim/getRecommendations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	@ResponseBody
	public String getRecommendations(HttpSession httpSession) throws Exception {
		UserIndividual currentUser = getCurrentUser(httpSession);
		List<Recommendation> recommendations = classifier.getRecommendations(currentUser);
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create().toJson(recommendations);
	}

}
