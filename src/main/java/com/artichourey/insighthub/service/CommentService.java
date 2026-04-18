package com.artichourey.insighthub.service;

import java.util.List;

import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;


public interface CommentService {

	
	CommentResponseDto addComment(Long postId, CommentRequestDto request, String username);

    List<CommentResponseDto> getCommentsByPostId(Long postId);

    void deleteComment(Long commentId, String name);
}
	

