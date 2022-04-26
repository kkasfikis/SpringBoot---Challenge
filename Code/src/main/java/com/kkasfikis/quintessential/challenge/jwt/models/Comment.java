package com.kkasfikis.quintessential.challenge.jwt.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private LocalDate dateCreated;

	public Comment() {
		
	}
	
	public Comment(String text, Post post, User user) {
		this.text = text;
		this.post = post;
		this.user = user; 
		SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		this.dateCreated = LocalDate.now();
		
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
