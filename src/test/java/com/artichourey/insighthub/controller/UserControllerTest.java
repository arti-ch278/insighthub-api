package com.artichourey.insighthub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.artichourey.insighthub.controllers.UserController;
import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.security.JwtUtil;
import com.artichourey.insighthub.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDto requestDto;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {

        requestDto = UserRequestDto.builder()
                .name("John")
                .email("john@test.com")
                .password("123456")
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
    void createUser_shouldReturnCreatedUser() throws Exception {

        when(userService.createUser(any(UserRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"));

        verify(userService).createUser(any(UserRequestDto.class));
    }

    @Test
    void getUser_shouldReturnUser() throws Exception {

        when(userService.getUserById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"));

        verify(userService).getUserById(1L);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {

        when(userService.updateUser(any(UserRequestDto.class), eq(1L)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

        verify(userService).updateUser(any(UserRequestDto.class), eq(1L));
    }

    @Test
    void deleteUser_shouldReturnSuccessMessage() throws Exception {

        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L)  
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                
                .andExpect(jsonPath("$.message").value("user deleted successfully"))
                .andExpect(jsonPath("$.success").value(true));

        verify(userService).deleteUser(1L);
    }
    
    @Test
    void createUser_shouldFail_whenInvalidEmail() throws Exception {

        requestDto.setEmail("invalid-email");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
