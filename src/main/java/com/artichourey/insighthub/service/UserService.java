package com.artichourey.insighthub.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;

public interface UserService {

	UserResponseDto createUser(UserRequestDto dto);
	UserResponseDto updateUser(UserRequestDto dto,Long id);
	Page<UserResponseDto> getAllUser(int page, int size);
	UserResponseDto getUserById(Long id);
	void deleteUser(Long id);
}
