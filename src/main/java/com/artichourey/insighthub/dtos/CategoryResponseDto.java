package com.artichourey.insighthub.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponseDto {
	private Long categoryId;
    private String categoryTitle;
    private String categoryDescription;
    private int postCount;
}
