
package com.artichourey.insighthub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.payloads.JwtAuthResponse;
import com.artichourey.insighthub.payloads.LoginRequest;
import com.artichourey.insighthub.security.JwtUtil;
import com.artichourey.insighthub.service.UserService;
import com.artichourey.insighthub.serviceImpl.CustomUserDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "User authentication and login")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailService customUserDetailService;
	private final JwtUtil jwtUtil;
	private final UserService userService;
	
	@Operation(summary = "Login user by username and password")
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request ){
		try {
			Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	final UserDetails userDetails=customUserDetailService.loadUserByUsername(request.getUsername());
	final String token=jwtUtil.generateToken(userDetails.getUsername());
	UserResponseDto  userResponseDto=userService.getUserByUserName(request.getUsername());
	JwtAuthResponse response=new JwtAuthResponse(token ,userResponseDto);
	return ResponseEntity.ok(response);
	}
}
