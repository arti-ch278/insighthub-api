package com.artichourey.insighthub.dtos;


import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @Schema(description = "Comment content", example = "This is a very helpful post!")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @Schema(description = "Parent comment ID (for replies)", example = "12")
    private Long parentCommentId;

    @Schema(description = "ID of the post on which comment is made", example = "101")
    private Long postId;
}

