package com.artichourey.insighthub.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CommentRequestDto {

   

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private LocalDateTime createdAt;

    private Long parentCommentId;

    //@NotNull(message = "Post ID is required")
    private Long postId;

  //  @NotNull(message = "User ID is required")
    private Long userId;

    private String username; // optional, can be ignored on creation

    private List<CommentRequestDto> replies = new ArrayList<>();
}

