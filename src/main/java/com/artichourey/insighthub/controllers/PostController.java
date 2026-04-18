package com.artichourey.insighthub.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.artichourey.insighthub.dtos.PostRequestDto;
import com.artichourey.insighthub.dtos.PostResponseDto;
import com.artichourey.insighthub.payloads.ApiResponse;
import com.artichourey.insighthub.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post APIs", description = "Operations related to blog posts")
public class PostController {
	
	private final PostService postService;
	@Operation(summary = "Create a new post")
	@PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody 
    		PostRequestDto postRequestDto, @PathVariable Long userId, @PathVariable Long categoryId) {

        PostResponseDto createdPost = postService.createPost(
                postRequestDto, userId,categoryId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
	
	@Operation(summary = "Update an existing post")
	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDto> updatePost(
	        @PathVariable Long postId,
	        @Valid @RequestBody PostRequestDto postRequestDto,
	        Principal principal) {

	    PostResponseDto updatedPost = postService.updatePost(postId, postRequestDto, principal.getName());
	    return ResponseEntity.ok(updatedPost);
	}
	
	@Operation(summary = "Get a post by post ID")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
	
	@Operation(summary = "Get all posts with pagination")
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostResponseDto> posts = postService.getAllPosts(page, size);
        return ResponseEntity.ok(posts);
    }
	
	@Operation(summary = "Delete a post by post ID")
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(
	        @PathVariable Long postId,
	        Principal principal) {

	    postService.deletePost(postId, principal.getName());
	    return ResponseEntity.ok(new ApiResponse("Post deleted successfully", true));
	}
	
	@Operation(summary = "Get all posts summary")
    @GetMapping("/summary")
    public ResponseEntity<List<PostResponseDto>> getAllPostSummary(){
    	return ResponseEntity.ok(postService.getAllPostSummary());
    }
	@Operation(summary = "Upload an image for a post")
    @PostMapping("/{postId}/upload-image")
    public ResponseEntity<PostResponseDto> uploadImage(@PathVariable Long postId ,@RequestParam("file") MultipartFile file) throws IOException{
    	
    	return ResponseEntity.ok(postService.uploadImage(postId, file));
    	
    }
    	
}
