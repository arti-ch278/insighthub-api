package com.artichourey.insighthub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.artichourey.insighthub.serviceImpl.PostServiceImpl;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private User user;
    private Category category;
    private Post post;
    private PostRequestDto requestDto;
    private PostResponseDto responseDto;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setName("John");

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("related Tech");

        requestDto = PostRequestDto.builder()
                .title("Test Post")
                .content("Content")
                .imageName("image.png")
                .build();

        post = Post.builder()
                .postId(1L)
                .title("Test Post")
                .content("Content")
                .user(user)
                .category(category)
                .build();

        responseDto = PostResponseDto.builder()
                .postId(1L)
                .title("Test Post")
                .content("Content")
                .userId(user.getId())
                .categoryId(category.getCategoryId())
                .build();
    }

    @Test
    void createPost_shouldReturnPostResponseDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(postMapper.toEntity(requestDto, user, category)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(responseDto);

        PostResponseDto result = postService.createPost(requestDto, 1L, 1L);

        assertNotNull(result);
        assertEquals("Test Post", result.getTitle());
        verify(postRepository).save(post);
    }

    @Test
    void getPostById_shouldThrowException_whenNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void deletePost_shouldCallRepositoryDelete() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(postRepository).delete(post);
    }
}