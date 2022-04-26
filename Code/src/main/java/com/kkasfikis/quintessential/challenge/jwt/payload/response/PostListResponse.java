package com.kkasfikis.quintessential.challenge.jwt.payload.response;

import java.util.ArrayList;
import java.util.List;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;

public class PostListResponse {
	private List<PostResponse> posts;

	public List<PostResponse> getPosts() {
		return posts;
	}
	
	public PostListResponse(List<Post> posts) {
		this.posts = new ArrayList<PostResponse>();
		if(posts != null && posts.size() > 0 ) {
			for(Post c : posts) {
				this.posts.add(new PostResponse(c.getId(),c.getTitle(),c.getText(),c.getUser().getFirstName(),c.getUser().getLastName()));
			}
		}
	}

	public void setPosts(List<PostResponse> posts) {
		this.posts = posts;
	}
	
}
