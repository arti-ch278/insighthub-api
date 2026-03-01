package com.artichourey.insighthub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryRequestDto {
	
	    @NotBlank(message = "Category title is required")
	    @Size(max = 100, message = "Category title cannot exceed 100 characters")
	    private String categoryTitle;

	    @Size(max = 500, message = "Category description cannot exceed 500 characters")
	    private String categoryDescription;

}
