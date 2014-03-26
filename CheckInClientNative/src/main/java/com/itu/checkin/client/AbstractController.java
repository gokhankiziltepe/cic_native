package com.itu.checkin.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.ModelAndView;

import com.itu.checkin.client.viewelements.PhoneForm;
import com.itu.checkin.client.viewelements.UserBaseForm;
import com.itu.checkin.client.viewelements.WorkingHourForm;
import com.itu.checkin.model.entity.phone.Phone;
import com.itu.checkin.model.entity.place.Place;
import com.itu.checkin.model.entity.place.workinghour.WorkingHour;
import com.itu.checkin.model.entity.user.type.UserCorporate;
import com.itu.checkin.model.entity.user.type.UserIndividual;
import com.itu.checkin.service.serviceinterface.PhoneService;
import com.itu.checkin.service.serviceinterface.UserCorporateService;
import com.itu.checkin.service.serviceinterface.UserIndividualService;
import com.itu.checkin.service.serviceinterface.WorkingHourService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Configuration
@PropertySource("classpath:/META-INF/spring/day_hour.properties")
public abstract class AbstractController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Environment env;

	@Autowired
	private UserCorporateService userCorporateService;

	@Autowired
	private WorkingHourService workingHourService;

	@Autowired
	private UserIndividualService userIndividualService;

	@Autowired
	private PhoneService phoneService;

	protected void authenticateUserAndSetSession(UserBaseForm userBaseForm,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				userBaseForm.getEmail(), userBaseForm.getPassword());

		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager
				.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	protected UserIndividual getCurrentUser(HttpSession httpSession) {
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

	protected Map<Short, String> getDayMap() {
		String[] workingDays = env.getProperty("working.days").split(",");
		Map<Short, String> dayMap = new TreeMap<Short, String>();
		for (short i = 0; i < workingDays.length; i++) {
			dayMap.put(i, workingDays[i]);
		}
		return dayMap;
	}

	protected Map<Short, String> getHourMap() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Map<Short, String> hourMap = new TreeMap<Short, String>();
		for (short i = 0; i < 48; i++) {
			hourMap.put(i, dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, 30);
		}
		return hourMap;
	}

	protected void addWorkingHours(Place place, ModelAndView modelAndView,
			WorkingHourForm workingHourForm) {
		ServiceResult<List<WorkingHour>> serviceResult = workingHourService
				.findByPlaceId(place.getId());
		if (serviceResult.getCodeConstant() == ServiceResultConstant.SUCCESS) {
			modelAndView
					.addObject("workingHourList", serviceResult.getResult());
		} else {
			modelAndView.addObject("isError", true);
			modelAndView.addObject("errorMessage", serviceResult.getMessage());
		}
		modelAndView.addObject("dayMap", getDayMap());
		modelAndView.addObject("hourMap", getHourMap());
		modelAndView.addObject("workingHourForm", workingHourForm);
		modelAndView.addObject("place", place);
	}

	protected void addPhones(Place place, ModelAndView modelAndView) {
		ServiceResult<List<Phone>> serviceResult = phoneService
				.findByOwnerEntityId(place.getId());
		if (serviceResult.getCodeConstant() == ServiceResultConstant.SUCCESS) {
			modelAndView.addObject("phoneList", serviceResult.getResult());
		} else {
			modelAndView.addObject("isError", true);
			modelAndView.addObject("errorMessage", serviceResult.getMessage());
		}
		modelAndView.addObject("phoneForm", new PhoneForm());
		modelAndView.addObject("place", place);
	}
}
