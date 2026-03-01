package com.artichourey.insighthub.mapper;

import org.springframework.stereotype.Component;

import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;
import com.artichourey.insighthub.entities.Category;

@Component
public class CategoryMapper {
	
	public Category toEntity(CategoryRequestDto dto) {
        return Category.builder()
                .categoryTitle(dto.getCategoryTitle())
                .categoryDescription(dto.getCategoryDescription())
                .build();
    }

    public CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .categoryDescription(category.getCategoryDescription())
                .postCount(category.getPosts() != null ? category.getPosts().size() : 0)
                .build();
    }

}
