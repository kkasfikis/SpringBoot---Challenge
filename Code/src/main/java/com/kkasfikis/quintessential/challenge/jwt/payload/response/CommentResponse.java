package com.kkasfikis.quintessential.challenge.jwt.payload.response;

public class CommentResponse {
	private long id;
	private String text;
	private String userFirstName;
	private String userLastName;
	private String postTitle;
	

	
	public CommentResponse(long id, String text, String userFirstName, String userLastName, String postTitle) {
		super();
		this.id = id;
		this.text = text;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.postTitle = postTitle;
	}
	
	
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
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	
	
}
