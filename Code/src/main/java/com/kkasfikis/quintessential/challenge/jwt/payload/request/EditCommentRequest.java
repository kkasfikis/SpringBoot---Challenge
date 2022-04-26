package com.kkasfikis.quintessential.challenge.jwt.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class EditCommentRequest {

	@Min(1)
	private long id;
	@NotBlank
	private String text;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
