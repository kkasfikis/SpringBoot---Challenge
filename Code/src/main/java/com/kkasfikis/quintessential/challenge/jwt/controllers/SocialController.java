package com.kkasfikis.quintessential.challenge.jwt.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkasfikis.quintessential.challenge.jwt.helpers.ErrorMessage;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;
import com.kkasfikis.quintessential.challenge.jwt.models.User;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.CreateCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.CreatePostRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.EditCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.EditPostRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.RemoveCommentRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.request.RemovePostRequest;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.CommentListResponse;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.MessageResponse;
import com.kkasfikis.quintessential.challenge.jwt.payload.response.PostListResponse;
import com.kkasfikis.quintessential.challenge.jwt.repository.CommentRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.PostRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.RoleRepository;
import com.kkasfikis.quintessential.challenge.jwt.repository.UserRepository;
import com.kkasfikis.quintessential.challenge.jwt.security.services.CommentService;
import com.kkasfikis.quintessential.challenge.jwt.security.services.PostService;
import com.kkasfikis.quintessential.challenge.jwt.security.services.RefreshTokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/social")
public class SocialController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentService commentService;

	@Autowired
	PostService postService;
	
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<?> getAllPosts(int page, int size) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(new PostListResponse(postService.getAllPosts(currUser.get().getId(), pageable)));   
	}
	
	
	@GetMapping("/getMyPosts")
	public ResponseEntity<?> getMyPosts(int page, int size) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(new PostListResponse( postService.getUserPosts(currUser.get().getId(), pageable)));   
	}
	
	@GetMapping("/getMyComments")
	public ResponseEntity<?> getMyComments(int page, int size) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		Pageable pageable = PageRequest.of(page, size);	
		return ResponseEntity.ok( new CommentListResponse(commentService.getUserComments(currUser.get().getId(),pageable )));   
	}
	
	@GetMapping("/getUserPosts")
	public ResponseEntity<?> getUserPosts(int userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(new PostListResponse( postService.getUserPosts(userId, pageable))); 
	}
	
	@GetMapping("/getPostComments")
	public ResponseEntity<?> getPostComments(int postId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);	
		return ResponseEntity.ok( new CommentListResponse(commentService.getPostComments(postId,pageable ))); 
	}
	
	
	
	@PostMapping("/createPost")
	public ResponseEntity<?> CreatePost(@Valid @RequestBody CreatePostRequest createPostRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		if(postService.createPost(currUser.get().getId(), createPostRequest) == null) {
			return ResponseEntity.ok(new MessageResponse("An error occured while creating post")); 
		}
		
		return ResponseEntity.ok(new MessageResponse("Post Created Successfully"));   
	}
	
	@PostMapping("/editPost")
	public ResponseEntity<?> EditPost(@Valid @RequestBody EditPostRequest editPostRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		System.out.println("editpost request id: " + editPostRequest.getId());
		
		if(postService.editPost(currUser.get().getId(), editPostRequest) == null) {
			return ResponseEntity.ok(new MessageResponse("An error occured while updating post"));   
		}
		return ResponseEntity.ok(new MessageResponse("Post Edited Successfully"));   
	}
	
	@PostMapping("/removePost")
	public ResponseEntity<?> RemovePost(@Valid @RequestBody RemovePostRequest removePostRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		if(!postService.removePost(currUser.get().getId(), removePostRequest)) {
			return ResponseEntity.ok(new MessageResponse("An error occured while removing post"));   
		}
		return ResponseEntity.ok(new MessageResponse("Post Deleted Successfully"));  
	}
	
	
	
	@PostMapping("/createComment")
	public ResponseEntity<?> CreateComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		if(commentService.createComment(currUser.get().getId(), createCommentRequest) == null) {
			return ResponseEntity.ok(new MessageResponse("An error occured while creating comment")); 
		}
		return ResponseEntity.ok(new MessageResponse("Comment Created Successfully"));   
	}
	
	@PostMapping("/editComment")
	public ResponseEntity<?> EditComent(@Valid @RequestBody EditCommentRequest editCommentRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		if(commentService.editComment(currUser.get().getId(), editCommentRequest) == null) {
			return ResponseEntity.ok(new MessageResponse("An error occured while updating comment"));   
		}
		return ResponseEntity.ok(new MessageResponse("Comment Edited Successfully"));   
	}
	
	@PostMapping("/removeComment")
	public ResponseEntity<?> RemoveComment(@Valid @RequestBody RemoveCommentRequest removeCommentRequest) {
		Optional<User> currUser = getLoggedInUser();
		if(!currUser.isPresent()) { return ResponseEntity.ok(new MessageResponse("Could not match current user!")); }
		
		if(!commentService.removeComment(currUser.get().getId(), removeCommentRequest)) {
			return ResponseEntity.ok(new MessageResponse("An error occured while removing comment"));   
		}
		return ResponseEntity.ok(new MessageResponse("Comment Deleted Successfully"));   
	}
	
	public Optional<User> getLoggedInUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByUsername(auth.getName());
	}
}
