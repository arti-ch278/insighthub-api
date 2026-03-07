package com.artichourey.insighthub.mapper;

import org.springframework.stereotype.Component;

import com.artichourey.insighthub.dtos.PostRequestDto;
import com.artichourey.insighthub.dtos.PostResponseDto;
import com.artichourey.insighthub.entities.Category;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;

@Component
public class PostMapper {

    public Post toEntity(PostRequestDto dto, User user, Category category) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .imageName(dto.getImageName())
                .user(user)
                .category(category)
                .build();
    }

    public PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageName(post.getImageName())
                .addedDate(post.getAddedDate())
          
                // existing IDs
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .categoryId(post.getCategory() != null ? post.getCategory().getCategoryId() : null)
                
                // new fields for front-end display
                .authorName(post.getUser() != null ? post.getUser().getName() : "unknown")
                .categoryName(post.getCategory() != null ? post.getCategory().getCategoryTitle() : "N/A")
                
                .build();
    }
}