package com.itu.checkin.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.itu.checkin.model.entity.place.workinghour.WorkingHour;
import com.itu.checkin.service.serviceinterface.WorkingHourService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class WorkingHourController extends AbstractController {

	@Autowired
	private WorkingHourService workingHourService;

	@RequestMapping(value = "/nativeapp/workinghour/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetCurrentUserPhoto(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "placeId") Integer placeId)
			throws ParseException {
		ServiceResult<List<WorkingHour>> serviceResult = workingHourService
				.findByPlaceId(placeId);
		List<WorkingHourItem> workingHourItems = new ArrayList<>();
		if (serviceResult.getResult() != null) {
			for (WorkingHour workingHour : serviceResult.getResult()) {
				workingHourItems.add(convertToWorkingHourItem(workingHour));
			}
		}
		return new GsonBuilder()
				.setDateFormat("hh:mm")
				.create()
				.toJson(new ServiceResult<List<WorkingHourItem>>(
						ServiceResultConstant.SUCCESS, "success",
						workingHourItems));

	}

	public class WorkingHourItem {
		private String start;
		private String end;
		private String day;

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}
	}

	private WorkingHourItem convertToWorkingHourItem(WorkingHour workingHour)
			throws ParseException {
		WorkingHourItem workingHourItem = new WorkingHourItem();
		workingHourItem.setDay(getDayMap().get(workingHour.getDay()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		Date start = dateFormat.parse(workingHour.getStartTime().toString());
		Date end = dateFormat.parse(workingHour.getEndTime().toString());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		workingHourItem.setStart(calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE));
		calendar.setTime(end);
		workingHourItem.setEnd(calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE));

		return workingHourItem;
	}
}
