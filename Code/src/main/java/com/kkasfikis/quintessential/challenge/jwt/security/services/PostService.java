package com.kkasfikis.quintessential.challenge.jwt.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;
import com.kkasfikis.quintessential.challenge.jwt.models.User;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.CreateCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.CreatePostRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.EditCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.EditPostRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.RemovePostRequest;
import com.kkasfikis.quintessential.challenge.jwt.repository.PostRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.UserRepository;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public Optional<Post> loadUserById(int id) {
		return postRepository.findById(id);
	}
	
	
	public Post createPost(long UserId, CreatePostRequest req) {
		Optional<User> user = userRepository.findById(UserId);
		if(!user.isPresent()) { return null;}
		
		Post post = new Post(req.getTitle(),req.getText(),user.get());
		post = postRepository.save(post);
		
		return post;
   }
	
	public Post editPost(long UserId,EditPostRequest req) {

		Optional<Post> c = postRepository.findById(req.getId()) ;
		Post post = c.isPresent() ? c.get() : null;

		if(post == null || post.getUser().getId() != UserId){ return null; }		
		
		post.setText(req.getText());
		
		post = postRepository.save(post);
		return post;
   }
	
   public boolean removePost(long UserId, RemovePostRequest req) {
	   Optional<User> user = userRepository.findById(UserId);
	   if(!user.isPresent()) {return false;}


	   Optional<Post> c = postRepository.findById(req.getId()) ;
	   Post post = c.isPresent() ? c.get() : null;
	   
	   if(post == null) {return false;}
	   
	   if(post.getUser().getId() == UserId) {	
		   postRepository.delete(post);
		   return true;
	   }
	   else {
		   return false;
	   }
   }
   
   public List<Post> getAllPosts(long UserId, Pageable pageable){
	   
	   Optional<User> user = userRepository.findById(UserId);
	   if(!user.isPresent()) {return null;}
	   
	   List<Post> posts = postRepository.findAllByOrderByDateCreatedAsc(pageable);
	   
	   return posts;
   }
   
   public List<Post> getUserPosts(long UserId, Pageable pageable){
	   
	   Optional<User> user = userRepository.findById(UserId);
	   if(!user.isPresent()) {return null;}
	   
	   List<Post> posts = postRepository.findByUser(user.get(),pageable);
	   
	   return posts;
   }
	
}
