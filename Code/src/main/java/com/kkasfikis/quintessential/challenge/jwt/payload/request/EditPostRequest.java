package com.kkasfikis.quintessential.challenge.jwt.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class EditPostRequest {

	@Min(1)
	private long id;
	@NotBlank
	private String title;
	@NotBlank
	private String text;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
