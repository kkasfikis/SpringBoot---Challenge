package com.kkasfikis.quintessential.challenge.jwt.payload.request;

import javax.validation.constraints.NotBlank;

public class CreatePostRequest {
	
	@NotBlank
	private String title;
	@NotBlank
	private String text;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
