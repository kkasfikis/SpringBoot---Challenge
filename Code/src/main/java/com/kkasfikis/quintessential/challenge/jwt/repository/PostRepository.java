package com.kkasfikis.quintessential.challenge.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kkasfikis.quintessential.challenge.jwt.models.Comment;
import com.kkasfikis.quintessential.challenge.jwt.models.ERole;
import com.kkasfikis.quintessential.challenge.jwt.models.Post;
import com.kkasfikis.quintessential.challenge.jwt.models.Role;
import com.kkasfikis.quintessential.challenge.jwt.models.User;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
	List<Post> findByUser(User user,Pageable pageable);
	Optional<Post> findById(int id);
	List<Post> findAllByOrderByDateCreatedAsc(Pageable pageable);
}
	