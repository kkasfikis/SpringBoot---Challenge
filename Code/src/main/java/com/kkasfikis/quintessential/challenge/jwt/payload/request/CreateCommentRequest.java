package com.kkasfikis.quintessential.challenge.jwt.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateCommentRequest {
	@NotBlank
	private String text;
	
	private long postId;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long post_id) {
		this.postId = post_id;
	}
}
