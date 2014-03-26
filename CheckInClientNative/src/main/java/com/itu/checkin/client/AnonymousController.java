package com.itu.checkin.client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itu.checkin.model.entity.EntityBase;
import com.itu.checkin.model.entity.user.UserBase;
import com.itu.checkin.model.entity.user.authority.UserAuthority;
import com.itu.checkin.model.entity.user.type.UserIndividual;
import com.itu.checkin.service.serviceinterface.PhotoService;
import com.itu.checkin.service.serviceinterface.UserIndividualService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class AnonymousController extends AbstractController {

	@Autowired
	private UserIndividualService userIndividualService;
	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "/nativeapp/user/save", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserSave(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "email") String email,
			@RequestParam(required = true, value = "password") String password,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = true, value = "surname") String surname,
			@RequestParam(required = true, value = "gender") String gender,
			@RequestParam(required = true, value = "birthDate") String birthDate)
			throws ParseException {
		Gson gson = new Gson();

		EntityBase entityBase = new EntityBase();

		UserBase userBase = new UserBase();
		userBase.setEmail(email);
		userBase.setEnabled(true);
		userBase.setEntityBase(entityBase);
		userBase.setName(name);
		userBase.setSurname(surname);
		userBase.setPassword(password);
		userBase.setRegDate(new Timestamp(new Date().getTime()));

		UserIndividual userIndividual = new UserIndividual();

		userIndividual.setGender(gender);
		userIndividual.setUserBase(userBase);
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setAuthorityRoleText("ROLE_IND");
		userAuthority.setEmail(userIndividual.getUserBase().getEmail());
		userAuthority.setEntityBase(new EntityBase());
		userAuthority.setUserBase(userIndividual.getUserBase());
		userIndividual.getUserBase().getUserAuthorities().add(userAuthority);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		userIndividual.setBirthDate(new Timestamp(dateFormat.parse(birthDate)
				.getTime()));

		return gson.toJson(userIndividualService.save(userIndividual));

	}

	@RequestMapping(value = "/loggedin", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpSession httpSession,
			@RequestParam(required = false, value = "authenticate", defaultValue = "false") Boolean authenticate,
			@RequestParam(required = false, value = "error", defaultValue = "false") Boolean error,
			@RequestParam(required = false, value = "success", defaultValue = "false") Boolean success,
			@RequestParam(required = false, value = "logout", defaultValue = "false") Boolean logout)
			throws ParseException {
		Gson gson = new Gson();
		ServiceResult<UserIndividual> serviceResult;
		if (authenticate) {
			serviceResult = new ServiceResult<UserIndividual>(
					ServiceResultConstant.FAIL, "Giriş yapmalısınız", null);
		} else if (error) {
			serviceResult = new ServiceResult<UserIndividual>(
					ServiceResultConstant.FAIL,
					"Kullanıcı adı veya şifre hatalı", null);
		} else if (success) {
			serviceResult = new ServiceResult<UserIndividual>(
					ServiceResultConstant.SUCCESS, "success",
					getCurrentUser(httpSession));
		} else if (logout) {
			serviceResult = new ServiceResult<UserIndividual>(
					ServiceResultConstant.SUCCESS, "Başarılı çıkış yapıldı",
					null);
		} else {
			serviceResult = new ServiceResult<UserIndividual>(
					ServiceResultConstant.FAIL, "Bilinmeyen bir hata oluştu",
					null);
		}

		return gson.toJson(serviceResult);

	}

	@RequestMapping(value = "/nativeapp/user", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetCurrentUser(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws ParseException {
		ServiceResult<UserIndividual> serviceResult = new ServiceResult<UserIndividual>(
				ServiceResultConstant.SUCCESS, "success",
				getCurrentUser(httpSession));
		return new GsonBuilder().setDateFormat("dd/MM/yyyy").create()
				.toJson(serviceResult);
	}

	@RequestMapping(value = "/nativeapp/user/update", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetCurrentUserUpdate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "password") String password,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = true, value = "surname") String surname,
			@RequestParam(required = true, value = "gender") String gender,
			@RequestParam(required = true, value = "birthDate") String birthDate)
			throws ParseException {
		UserIndividual userIndividual = getCurrentUser(httpSession);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		userIndividual.setBirthDate(new Timestamp(dateFormat.parse(birthDate)
				.getTime()));
		userIndividual.setGender(gender);
		userIndividual.getUserBase().setPassword(password);
		userIndividual.getUserBase().setName(name);
		userIndividual.getUserBase().setSurname(surname);
		return new GsonBuilder().setDateFormat("dd/MM/yyyy").create()
				.toJson(userIndividualService.save(userIndividual));
	}

}
