package com.kkasfikis.quintessential.challenge.jwt.security.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;
import com.kkasfikis.quintessential.challenge.jwt.models.RefreshToken;
import com.kkasfikis.quintessential.challenge.jwt.models.User;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.CreateCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.EditCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.RemoveCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.repository.CommentRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.PostRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.UserRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public Optional<Comment> loadUserById(int id) {
		return commentRepository.findById(id);
	}
	
	public Comment createComment(long UserId, CreateCommentRequest req) {
		Optional<User> user = userRepository.findById(UserId);
		if(!user.isPresent()) { return null;}

		Optional<Post> post = postRepository.findById(req.getPostId());
		if(!post.isPresent()) { return null;}
		
		
		
		Comment comment = new Comment(req.getText(),post.get(),user.get());
		comment = commentRepository.save(comment);
		
		
		
		return comment;
   }
	
	public Comment editComment(long UserId, EditCommentRequest req) {
		Optional<Comment> c = commentRepository.findById(req.getId()) ;
		Comment comment = c.isPresent() ? c.get() : null;
		
		if(comment == null || comment.getUser().getId() != UserId){ return null; }
		
		comment.setText(req.getText());
		
		comment = commentRepository.save(comment);
		return comment;
   }
	
   public boolean removeComment(long UserId,RemoveCommentRequest req) {
	   Optional<User> user = userRepository.findById(UserId);
	   if(!user.isPresent()) {return false;}

	   Optional<Comment> c = commentRepository.findById(req.getId()) ;
	   Comment comment = c.isPresent() ? c.get() : null;
	   
	   if(comment == null) {return false;}
	   
	   if(comment.getUser().getId() == UserId) {	
		   commentRepository.delete(comment);
		   return true;
	   }
	   else {
		   return false;
	   }
   }
   
   public List<Comment> getUserComments(long UserId, Pageable pageable){
	   
	   Optional<User> user = userRepository.findById(UserId);
	   if(!user.isPresent()) {return null;}
	   
	   List<Comment> comments = commentRepository.findByUser(user.get(),pageable);
	   
	   return comments;
   }
   
   public List<Comment> getPostComments(long PostId, Pageable pageable){
	   
	   Optional<Post> post = postRepository.findById(PostId);
	   if(!post.isPresent()) {return null;}
	   
	   List<Comment> comments = commentRepository.findByPost(post.get(),pageable);
	   
	   return comments;
   }
	
}
