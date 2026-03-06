package com.artichourey.insighthub.service;

import java.util.List;

import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.entities.User;

public interface CommentService {

	
	CommentResponseDto addComment(Long postId, CommentRequestDto request, User user);

 //   CommentResponseDto replyToComment(Long parentCommentId, CommentRequestDto request);

    List<CommentResponseDto> getCommentsByPostId(Long postId);

    void deleteComment(Long commentId);
}
	

