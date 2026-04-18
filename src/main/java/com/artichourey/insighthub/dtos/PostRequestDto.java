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
public class PostRequestDto {

	@Schema(
	        description = "Title of the blog post",
	        example = "Introduction to Spring Boot")
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

	@Schema(
	        description = "Main content of the blog post",
	        example = "Spring Boot makes it easy to create stand-alone production-grade applications..."
	    )
    @NotBlank(message = "Content is required")
    @Size(max = 10000, message = "Content cannot exceed 10,000 characters")
    private String content;
	
	 @Schema(
		        description = "Optional image name or URL",
		        example = "springboot-intro.png"
		    )
    private String imageName; 

}