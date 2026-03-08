package com.artichourey.insighthub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.payloads.ApiResponse;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	private final UserRepository userRepository;
	
	@PostMapping("/{postId}")
	public ResponseEntity<?> addCreate(@PathVariable Long postId, 
			@Valid @RequestBody CommentRequestDto dto, @AuthenticationPrincipal UserDetails userDetails ){
		if(userDetails==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required for comment");
		}
		User user=userRepository.findByName(userDetails.getUsername()).orElseThrow(()->new ResourceNotFoundException("user not found"));
		CommentResponseDto saved=commentService.addComment(postId, dto, user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		
	}
	@GetMapping("/{postId}")
	public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable Long postId){
		List<CommentResponseDto> list =commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok(list);
		
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
		commentService.deleteComment(commentId);
		return ResponseEntity.ok(new ApiResponse("user deleted successfully",true));
		
	}

}
