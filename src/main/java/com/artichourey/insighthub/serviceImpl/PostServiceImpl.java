package com.artichourey.insighthub.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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


}
