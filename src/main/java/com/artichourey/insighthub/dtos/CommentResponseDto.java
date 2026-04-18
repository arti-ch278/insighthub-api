package com.artichourey.insighthub.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long parentCommentId;
    private Long postId;
    private Long userId;
    private String username;
    private List<CommentResponseDto> replies = new ArrayList<>();
}