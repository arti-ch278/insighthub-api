package com.artichourey.insighthub.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryRequestDto {

    @Schema(
        description = "Title of the category",
        example = "Technology"
    )
    @NotBlank(message = "Category title is required")
    @Size(max = 100, message = "Category title cannot exceed 100 characters")
    private String categoryTitle;

    @Schema(
        description = "Short description of the category",
        example = "All posts related to software development and tech trends"
    )
    @Size(max = 500, message = "Category description cannot exceed 500 characters")
    private String categoryDescription;
}


