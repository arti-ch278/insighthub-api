package com.artichourey.insighthub.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime addedDate;
    private Long userId;     
    private Long categoryId;  
    private String categoryName; 
    private String authorName;  
}