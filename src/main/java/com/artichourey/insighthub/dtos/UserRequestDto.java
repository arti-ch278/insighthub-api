package com.artichourey.insighthub.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

	@Schema(
		        description = "Full name of the user",
		        example = "John Doe"
		    )
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;
	
	 @Schema(
		        description = "Email address of the user",
		        example = "john@example.com"
		    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Schema(
		        description = "User password (min 6 characters)",
		        example = "password123"
		    )
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(
            description = "Short bio or about section",
            example = "Software developer passionate about Java and Spring Boot"
        )
    @Size(max = 500)
    private String about;
}