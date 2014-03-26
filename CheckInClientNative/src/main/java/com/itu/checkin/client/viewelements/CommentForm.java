package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
public class CommentForm implements Serializable {
	private String commentText;

	@NotNull(message = "yorum metni boş olamaz")
	@Size(max = 1000, message = "yorum uzun girildi.")
	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

}
