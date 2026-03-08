package com.artichourey.insighthub.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artichourey.insighthub.dtos.PostRequestDto;
import com.artichourey.insighthub.dtos.PostResponseDto;
import com.artichourey.insighthub.entities.Category;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.PostMapper;
import com.artichourey.insighthub.repositories.CategoryRepository;
import com.artichourey.insighthub.repositories.PostRepository;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.service.PostService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final PostMapper postMapper;
	private static final int SUMMARY_LENGTH = 100;
	private final String uploadDir = System.getProperty("user.dir") + "/uploads/posts/";

	@Override
	public PostResponseDto createPost(PostRequestDto postRequestDto, Long userId, Long categoryId) {
		log.info("Creating new post with title: '{}' by userId: {} and categoryId: {}", 
                postRequestDto.getTitle(), userId, categoryId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this "+ userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with this "+categoryId));

        Post post = postMapper.toEntity(postRequestDto, user, category);
        post.setAddedDate(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with postId: {}", savedPost.getPostId());

        return postMapper.toDto(savedPost);
    }

	@Override
	public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
		log.info("Updating post with postId: {}", postId);
		Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post is not found with this "+postId));

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setImageName(postRequestDto.getImageName());

        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully with postId: {}", postId);
        return postMapper.toDto(updatedPost);
		
	}

	@Override
	public PostResponseDto getPostById(Long postId) {
		log.info("Fetching post with postId: {}", postId);
		 Post post=postRepository.findById(postId)
				 .orElseThrow(()-> new ResourceNotFoundException("post is not found with this"+postId));
		 log.info("Post fetched successfully with postId: {}", postId);
		 
		return postMapper.toDto(post);
	}

	@Override
	public Page<PostResponseDto> getAllPosts(int pageNumber, int pageSize) {
		log.info("Fetching all posts - pageNumber: {}, pageSize: {}", pageNumber, pageSize);

	    // Sort posts by addedDate descending (newest first)
	    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("addedDate").descending());

	    // Fetch paginated posts
	    Page<Post> postsPage = postRepository.findAll(pageRequest);

	    // Map Post entities to PostResponseDto
	    log.info("Fetched {} posts on page {}", postsPage.getNumberOfElements(), pageNumber);
	    return postsPage.map(postMapper::toDto);
	}

	@Override
	public void deletePost(Long postId) {
		log.info("Deleting post with postId: {}", postId);
		Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post"+postId));

        postRepository.delete(post);
        log.info("Post deleted successfully with postId: {}", postId);
		
	}

	@Override
	public List<PostResponseDto> getAllPostSummary() {

	    log.info("Fetching all posts for summary view");

	    List<PostResponseDto> postSummaries = postRepository.findAll()
	            .stream()
	            .map(post -> {

	                PostResponseDto dto = postMapper.toDto(post);

	                if (dto.getContent() != null && dto.getContent().length() > SUMMARY_LENGTH) {
	                    dto.setContent(dto.getContent().substring(0, SUMMARY_LENGTH) + "...");
	                }

	                return dto;
	            })
	            .collect(Collectors.toList());

	    log.info("Successfully generated summaries for {} posts", postSummaries.size());

	    return postSummaries;
	}

	@Override
	public PostResponseDto uploadImage(Long postId, MultipartFile file) throws IOException {
	    log.info("Starting image upload for postId={}", postId);

	    Post post = postRepository.findById(postId)
	            .orElseThrow(() -> {
	                log.error("Post not found for id={}", postId);
	                return new ResourceNotFoundException("Post not found " + postId);
	            });

	    if (file.isEmpty()) {
	        log.warn("Uploaded file is empty for postId={}", postId);
	        throw new RuntimeException("Image file is empty");
	    }

	    long maxSize = 2 * 1024 * 1024; // 2MB
	    if (file.getSize() > maxSize) {
	        log.warn("File size too large: {} bytes for postId={}", file.getSize(), postId);
	        throw new RuntimeException("File size must be < 2MB");
	    }

	    List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/jpg");
	    if (!allowedTypes.contains(file.getContentType())) {
	        log.warn("Unsupported content type '{}' for postId={}", file.getContentType(), postId);
	        throw new RuntimeException("Only JPEG/PNG/JPG allowed");
	    }

	    String originalName = file.getOriginalFilename();
	    if (originalName == null || !originalName.contains(".")) {
	        log.warn("Invalid file name for postId={}", postId);
	        throw new RuntimeException("Invalid file name");
	    }

	    String ext = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
	    List<String> allowedExt = List.of("jpg", "jpeg", "png");
	    if (!allowedExt.contains(ext)) {
	        log.warn("Invalid file extension '{}' for postId={}", ext, postId);
	        throw new RuntimeException("Invalid image extension");
	    }

	    File folder = new File(uploadDir);
	    if (!folder.exists()) {
	        boolean created = folder.mkdirs();
	        log.info("Upload folder {} created: {}", uploadDir, created);
	    }

	    String fileName = UUID.randomUUID().toString() + "." + ext;
	    Path filePath = Paths.get(uploadDir, fileName);
	    try {
	        Files.write(filePath, file.getBytes());
	        log.info("File written successfully: {}", filePath.toAbsolutePath());
	    } catch (IOException e) {
	        log.error("Failed to save file: {}", fileName, e);
	        throw new RuntimeException("Failed to save file: " + fileName, e);
	    }

	    String imageUrl = "/posts/images/" + fileName;
	    post.setImageName(imageUrl);
	    log.info("Image URL set for postId={}: {}", postId, imageUrl);

	    Post saved = postRepository.save(post);
	    log.info("Image uploaded and post saved successfully for postId={}", postId);

	    return postMapper.toDto(saved);
	}
}