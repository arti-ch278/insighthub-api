package com.artichourey.insighthub.mapper;

import org.springframework.stereotype.Component;

import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.entities.User;

@Component
public class UserMapper {
	
	public User toEntity(UserRequestDto dto) {
		return User.builder().name(dto.getName()).email(dto.getEmail()).password(dto.getPassword())
				.about(dto.getAbout()).build();

}
	
	public UserResponseDto toDto(User user) {
		  return UserResponseDto.builder()
	            .id(user.getId())
	            .name(user.getName())
	            .email(user.getEmail())
	            .about(user.getAbout())
	            .build();
	}
}