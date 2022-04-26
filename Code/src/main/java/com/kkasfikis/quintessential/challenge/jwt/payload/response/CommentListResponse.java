package com.kkasfikis.quintessential.challenge.jwt.payload.response;

import java.util.ArrayList;
import java.util.List;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;

public class CommentListResponse {
	public List<CommentResponse> comments;

	public CommentListResponse(List<Comment> comments) {
		this.comments = new ArrayList<CommentResponse>();
		if(comments != null && comments.size() > 0 ) {
			for(Comment c : comments) {
				this.comments.add(new CommentResponse(c.getId(),c.getText(),c.getUser().getFirstName(),c.getUser().getLastName(),c.getPost().getTitle()));
			}
		}
		
	}
	
	public List<CommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponse> comments) {
		this.comments = comments;
	}
	
}

