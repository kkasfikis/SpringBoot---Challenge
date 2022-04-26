package com.kkasfikis.quintessential.challenge.jwt.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String title;

	@NotBlank
	@Size(max = 50)
	private String text;
	

	private LocalDate dateCreated;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@OneToMany(
        orphanRemoval = true, mappedBy = "post"
    )
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
	
	public Post() {
		
	}
	
	public  Post(String title, String text, User user) {
		this.title = title;
		this.text = text;
		this.user = user; 
		this.dateCreated = LocalDate.now();
	}
	
	
	
	public Long getId() {
		return id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	

	public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
 
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}
