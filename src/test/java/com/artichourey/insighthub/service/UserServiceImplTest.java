package com.artichourey.insighthub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.UserMapper;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class) 
public class UserServiceImplTest {
	
	    @Mock
	    private UserRepository userRepository;

	    @Mock
	    private UserMapper userMapper;

	    @Mock
	    private BCryptPasswordEncoder passwordEncoder;

	    @InjectMocks
	    private UserServiceImpl userService;

	    private User user;
	    private UserRequestDto requestDto;
	    private UserResponseDto responseDto;

	    @BeforeEach
	    void setUp() {
	        requestDto = UserRequestDto.builder()
	                .name("John")
	                .email("john@test.com")
	                .password("1234")
	                .about("about")
	                .build();

	        user = User.builder()
	                .id(1L)
	                .name("John")
	                .email("john@test.com")
	                .password("encoded")
	                .about("about")
	                .build();

	        responseDto = UserResponseDto.builder()
	                .id(1L)
	                .name("John")
	                .email("john@test.com")
	                .about("about")
	                .build();
	    }

	    @Test
	    void createUser_shouldReturnUserResponseDto() {
	        when(userMapper.toEntity(requestDto)).thenReturn(user);
	        when(passwordEncoder.encode("1234")).thenReturn("encoded");
	        when(userRepository.save(any(User.class))).thenReturn(user);
	        when(userMapper.toDto(user)).thenReturn(responseDto);

	        UserResponseDto result = userService.createUser(requestDto);

	        assertNotNull(result);
	        assertEquals("John", result.getName());
	        verify(userRepository).save(any(User.class));
	    }

	    @Test
	    void getUserById_shouldThrowException_whenUserNotFound() {
	        when(userRepository.findById(1L)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class,
	                () -> userService.getUserById(1L));
	    }

	    @Test
	    void deleteUser_shouldDeleteSuccessfully() {
	        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

	        userService.deleteUser(1L);

	        verify(userRepository).delete(user);
	    }
	}


