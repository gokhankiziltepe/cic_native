package com.itu.checkin.client;

import java.io.Serializable;
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
import com.itu.checkin.model.entity.user.like.Like;
import com.itu.checkin.service.serviceinterface.EntityBaseService;
import com.itu.checkin.service.serviceinterface.LikeService;
import com.itu.checkin.service.serviceresult.ServiceResult;
import com.itu.checkin.service.serviceresult.constant.ServiceResultConstant;

@Controller
public class LikeDislikeController extends AbstractController {

	@Autowired
	private LikeService likeService;
	@Autowired
	private EntityBaseService entityBaseService;

	@RequestMapping(value = "/nativeapp/likedislike/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountUserGetCurrentUserPhoto(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {
		ServiceResult<List<Like>> serviceResult = likeService
				.findByLikedEntityBaseId(id);
		LikeDislike likeDislike = new LikeDislike();
		likeDislike.setLikeCount(0);
		likeDislike.setDislikeCount(0);
		if (serviceResult.getResult() != null
				&& serviceResult.getResult().size() > 0) {
			for (Like like : serviceResult.getResult()) {
				if (like.getIsLike()) {
					likeDislike.setLikeCount(likeDislike.getLikeCount() + 1);
				} else {
					likeDislike
							.setDislikeCount(likeDislike.getDislikeCount() + 1);
				}
			}
		}
		return new GsonBuilder().create().toJson(
				new ServiceResult<LikeDislike>(ServiceResultConstant.SUCCESS,
						"success", likeDislike));

	}

	@RequestMapping(value = "/nativeapp/likedislike", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=*/*")
	public @ResponseBody
	String myAccountLike(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession httpSession,
			@RequestParam(required = true, value = "isLike") Boolean isLike,
			@RequestParam(required = true, value = "id") Integer id)
			throws ParseException {
		Like like = new Like();
		like.setEntityBase(new EntityBase());
		like.setDateAdded(new Timestamp(new Date().getTime()));
		like.setIsLike(isLike);
		like.setUserIndividual(getCurrentUser(httpSession));
		like.setLikedEntityBase(entityBaseService.findById(id).getResult());

		return new GsonBuilder().setDateFormat("dd/MM/yyyy").create()
				.toJson(likeService.save(like));

	}

	@SuppressWarnings("serial")
	public class LikeDislike implements Serializable {
		private Integer likeCount;
		private Integer dislikeCount;

		public Integer getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(Integer likeCount) {
			this.likeCount = likeCount;
		}

		public Integer getDislikeCount() {
			return dislikeCount;
		}

		public void setDislikeCount(Integer dislikeCount) {
			this.dislikeCount = dislikeCount;
		}

	}
}
