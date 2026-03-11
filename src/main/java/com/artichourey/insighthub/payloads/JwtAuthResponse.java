package com.artichourey.insighthub.payloads;

import com.artichourey.insighthub.dtos.UserResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
	
	private String token;
	private UserResponseDto user;

}