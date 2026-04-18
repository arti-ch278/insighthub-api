package com.artichourey.insighthub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artichourey.insighthub.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
  
	List<Comment> findByPostPostId(Long postId);
	
	
	
	
}
