package com.itu.checkin.client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.itu.checkin.model.entity.user.comment.Comment;
import com.itu.checkin.service.serviceinterface.CommentService;
import com.itu.checkin.service.serviceinterface.EntityBaseService;
import com.itu.checkin.service.serviceinterface.PhotoService;
import com.itu.checkin.service.serviceinterface.PlaceService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class CommentController extends AbstractController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private EntityBaseService entityBaseService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private PhotoService photoService;

	@RequestMapping(value = "/nativeapp/comment/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountGetComments(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {

		ServiceResult<List<Comment>> comments = commentService
				.findByCommentedToEntityBaseId(id);
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(comments);

	}

	@RequestMapping(value = "/nativeapp/comment/save", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountSaveComment(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpSession httpSession,
			@RequestParam(required = true, value = "commentText") String commentText,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {
		Comment comment = new Comment();
		comment.setCommentedByUserBase(getCurrentUser(httpSession)
				.getUserBase());
		comment.setCommentedToEntityBase(entityBaseService.findById(id)
				.getResult());
		comment.setCommentText(commentText);
		comment.setDateAdded(new Timestamp(new Date().getTime()));
		comment.setEntityBase(new EntityBase());
		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(commentService.save(comment));

	}

	@RequestMapping(value = "/nativeapp/comment/place/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountCommentPlaceGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws ParseException {
		ServiceResult<List<Comment>> comments = commentService
				.findByUserBaseId(getCurrentUser(httpSession).getId());
		List<Comment> placeComments = new ArrayList<Comment>();
		for (Comment comment : comments.getResult()) {
//			if (placeService.exists(comment.getCommentedToEntityBase().getId())
//					.getResult()) {
//				placeComments.add(comment);
//			}
		}
		return new GsonBuilder()
				.setDateFormat("dd/MM/yyyy hh:mm")
				.create()
				.toJson(new ServiceResult<List<Comment>>(
						ServiceResultConstant.SUCCESS, "success", placeComments));

	}

	@RequestMapping(value = "/nativeapp/comment/photo/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountCommentPhotoGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession)
			throws ParseException {
		ServiceResult<List<Comment>> comments = commentService
				.findByUserBaseId(getCurrentUser(httpSession).getId());
		List<Comment> photoComments = new ArrayList<Comment>();
		for (Comment comment : comments.getResult()) {
//			if (photoService.exists(comment.getCommentedToEntityBase().getId())
//					.getResult()) {
//				photoComments.add(comment);
//			}
		}
		return new GsonBuilder()
				.setDateFormat("dd/MM/yyyy hh:mm")
				.create()
				.toJson(new ServiceResult<List<Comment>>(
						ServiceResultConstant.SUCCESS, "success", photoComments));

	}
	
	@RequestMapping(value = "/nativeapp/comment/delete", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountCheckInDelete(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpSession httpSession,
			@RequestParam(required = true, value = "commentId") Integer commentId)
			throws ParseException {

		return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").create()
				.toJson(commentService.delete(commentId));

	}
}
