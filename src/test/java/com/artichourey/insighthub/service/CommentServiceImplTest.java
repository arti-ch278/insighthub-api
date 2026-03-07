package com.artichourey.insighthub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.entities.Comment;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.CommentMapper;
import com.artichourey.insighthub.repositories.CommentRepository;
import com.artichourey.insighthub.repositories.PostRepository;
import com.artichourey.insighthub.serviceImpl.CommentServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Post post;
    private User user;
    private Comment comment;
    private CommentRequestDto requestDto;
    private CommentResponseDto responseDto;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).name("John").build();
        post = Post.builder().postId(1L).title("Test Post").user(user).build();

        requestDto = CommentRequestDto.builder().content("Nice post!").build();

        comment = Comment.builder().id(1L).content("Nice post!").post(post).user(user).build();

        responseDto = CommentResponseDto.builder()
                .id(1L)
                .content("Nice post!")
                .postId(post.getPostId())
                .userId(user.getId())
                .username(user.getName())
                .build();
    }

    @Test
    void addComment_shouldReturnCommentResponseDto() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentMapper.toEntity(requestDto, user, post, null)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.addComment(1L, requestDto, user);

        assertNotNull(result);
        assertEquals("Nice post!", result.getContent());
        verify(commentRepository).save(comment);
    }

    @Test
    void getCommentsByPostId_shouldReturnList() {
        when(commentRepository.findByPostPostId(1L)).thenReturn(List.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        List<CommentResponseDto> comments = commentService.getCommentsByPostId(1L);

        assertEquals(1, comments.size());
        assertEquals("Nice post!", comments.get(0).getContent());
    }

    @Test
    void deleteComment_shouldCallRepositoryDelete() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);

        verify(commentRepository).delete(comment);
    }

    @Test
    void addComment_shouldThrowException_whenPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> commentService.addComment(1L, requestDto, user));
    }
}
