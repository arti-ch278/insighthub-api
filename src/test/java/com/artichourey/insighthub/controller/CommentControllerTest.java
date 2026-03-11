package com.artichourey.insighthub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.artichourey.insighthub.controllers.CommentController;
import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.security.JwtUtil;
import com.artichourey.insighthub.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.test.context.support.WithMockUser;



@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil; 
    
    @MockBean
    private CommentService commentService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentRequestDto requestDto;
    private CommentResponseDto responseDto;
    private User user;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).name("John").build();

        requestDto = CommentRequestDto.builder().content("Nice post!").build();

        responseDto = CommentResponseDto.builder()
                .id(1L)
                .content("Nice post!")
                .postId(1L)
                .userId(1L)
                .username("John")
                .build();
    }

    @Test
    @WithMockUser(username = "John")
    void addComment_shouldReturnCreatedComment() throws Exception {

        when(commentService.addComment(eq(1L), any(CommentRequestDto.class), eq("John")))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/comments/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Nice post!"))
                .andExpect(jsonPath("$.userId").value(1));
    }
    
    @Test
    void getComments_shouldReturnList() throws Exception {
        when(commentService.getCommentsByPostId(1L)).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/comments/{postId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Nice post!"));
    }

    @Test
    @WithMockUser(username = "John")
    void deleteComment_shouldReturnSuccessMessage() throws Exception {
        doNothing().when(commentService).deleteComment(1L, "John");

        mockMvc.perform(delete("/api/comments/{commentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Comment deleted successfully"));

        verify(commentService).deleteComment(1L, "John");
    }
}
