package com.artichourey.insighthub.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.payloads.ApiResponse;
import com.artichourey.insighthub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
		UserResponseDto saved=userService.createUser(userRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		
	}
	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long userId){
		UserResponseDto update=userService.updateUser(userRequestDto, userId);
		return ResponseEntity.ok(update) ;	
	}
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
		UserResponseDto user=userService.getUserById(userId);
		return ResponseEntity.ok(user);	
	}
	@GetMapping
	public ResponseEntity<Page<UserResponseDto>> getAllUser( @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size){
		Page<UserResponseDto> user=userService.getAllUser(page, size);
		return ResponseEntity.ok(user);	
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return ResponseEntity.ok(new ApiResponse("user deleted successfully",true));

}
}
