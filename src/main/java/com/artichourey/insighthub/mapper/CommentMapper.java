package com.artichourey.insighthub.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.artichourey.insighthub.dtos.CommentRequestDto;
import com.artichourey.insighthub.dtos.CommentResponseDto;
import com.artichourey.insighthub.entities.Comment;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;

@Component
public class CommentMapper {

    // Entity -> Response DTO
    public CommentResponseDto toDto(Comment comment) {
        if (comment == null) return null;

        CommentResponseDto dto = CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parentCommentId(
                        comment.getParentComment() != null
                                ? comment.getParentComment().getId()
                                : null
                )
                .postId(
                        comment.getPost() != null
                                ? comment.getPost().getPostId()
                                : null
                )
                .userId(
                        comment.getUser() != null
                                ? comment.getUser().getId()
                                : null
                )
                .username(
                        comment.getUser() != null
                                ? comment.getUser().getName()
                                : null
                )
                .build();

        //  Keep replies mapping (response side only)
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            List<CommentResponseDto> replyDtos = comment.getReplies().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            dto.setReplies(replyDtos);
        }

        return dto;
    }

    //  Request DTO -> Entity 
    public Comment toEntity(CommentRequestDto dto, User user, Post post, Comment parentComment) {
        if (dto == null) return null;

        return Comment.builder()
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .parentComment(parentComment)

                .build();
    }
}