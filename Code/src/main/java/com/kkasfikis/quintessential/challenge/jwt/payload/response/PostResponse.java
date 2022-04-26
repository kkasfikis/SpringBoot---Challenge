package com.kkasfikis.quintessential.challenge.jwt.payload.response;

public class PostResponse {
	private long id;
	private String title;
	private String text;
	private String userFirstName;
	private String userLastName;
	

	public PostResponse(long id, String title, String text, String userFirstName, String userLastName) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
	}
	
	
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
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	
}
