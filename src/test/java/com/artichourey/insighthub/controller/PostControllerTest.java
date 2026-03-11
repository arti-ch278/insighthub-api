package com.artichourey.insighthub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.artichourey.insighthub.controllers.PostController;
import com.artichourey.insighthub.dtos.PostRequestDto;
import com.artichourey.insighthub.dtos.PostResponseDto;
import com.artichourey.insighthub.security.JwtUtil;
import com.artichourey.insighthub.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private JwtUtil jwtUtil; // mock JWT dependency if exists

    @Autowired
    private ObjectMapper objectMapper;

    private PostRequestDto requestDto;
    private PostResponseDto responseDto;

    @BeforeEach
    void setup() {
        requestDto = PostRequestDto.builder()
                .title("Test Post")
                .content("Content")
                .imageName("image.png")
                .build();

        responseDto = PostResponseDto.builder()
                .postId(1L)
                .title("Test Post")
                .content("Content")
                .userId(1L)
                .categoryId(1L)
                .build();
    }

    @Test
    void createPost_shouldReturnCreatedPost() throws Exception {
        when(postService.createPost(any(PostRequestDto.class), eq(1L), eq(1L)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/posts/user/{userId}/category/{categoryId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.postId").value(1));
    }

    @Test
    void deletePost_shouldReturnSuccessMessage() throws Exception {
        
        Principal principal = () -> "testuser";  // username of the logged-in user

        
        doNothing().when(postService).deletePost(1L, "testuser");

        mockMvc.perform(delete("/api/posts/{postId}", 1L)
                .principal(principal))  // pass the mocked logged-in user
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Post deleted successfully"));

        verify(postService).deletePost(1L, "testuser");
    }
}
