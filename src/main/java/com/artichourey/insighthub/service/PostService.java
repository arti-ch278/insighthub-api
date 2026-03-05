package com.artichourey.insighthub.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.artichourey.insighthub.dtos.PostRequestDto;
import com.artichourey.insighthub.dtos.PostResponseDto;

public interface PostService {
	
	PostResponseDto createPost (PostRequestDto postRequestDto, Long userId, Long categoryId);
	PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto);
	PostResponseDto getPostById(Long postId);
	Page<PostResponseDto> getAllPosts(int pageNumber, int pageSize);
	void deletePost(Long postId);
	List<PostResponseDto> getAllPostSummary();
	

}
