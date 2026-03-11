package com.artichourey.insighthub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.payloads.ApiResponse;
import com.artichourey.insighthub.service.CommentService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment APIs", description = "Operations related to comments")
public class CommentController {
	
	private final CommentService commentService;
	
	@Operation(summary = "Add a comment to a post")
	@PostMapping("/{postId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> createComment(@PathVariable Long postId, 
			@Valid @RequestBody CommentRequestDto dto, @AuthenticationPrincipal UserDetails userDetails ){
		
		CommentResponseDto saved=commentService.addComment(postId, dto, userDetails.getUsername() );
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		
	}
	@Operation(summary = "Get comments by post ID")
	@GetMapping("/{postId}")
	public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable Long postId){
		List<CommentResponseDto> list =commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok(list);
		
	}
	@Operation(summary = "Delete a comment by comment ID")
	//AuthenticationPrincipal to get current logged-in user
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
	        @PathVariable Long commentId,
	        @AuthenticationPrincipal UserDetails currentUser 
	) {
	    commentService.deleteComment(commentId, currentUser.getUsername());
	    return ResponseEntity.ok(new ApiResponse("Comment deleted successfully", true));
	}

}
