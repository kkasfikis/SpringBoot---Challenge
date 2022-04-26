package com.kkasfikis.quintessential.challenge.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;
import com.kkasfikis.quintessential.challenge.jwt.models.RefreshToken;
import com.kkasfikis.quintessential.challenge.jwt.models.User;

@Repository
public interface CommentRepository  extends PagingAndSortingRepository<Comment, Long>{
	Optional<Comment> findById(int id);
	List<Comment> findByPost(Post post,Pageable pageable);
	List<Comment> findByUser(User user,Pageable pageable);
}
