package com.artichourey.insighthub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.artichourey.insighthub.controllers.CategoryController;
import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;
import com.artichourey.insighthub.security.JwtUtil;
import com.artichourey.insighthub.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private JwtUtil jwtUtil;
    
    @Autowired
    private ObjectMapper objectMapper;

    private CategoryRequestDto requestDto;
    private CategoryResponseDto responseDto;

    @BeforeEach
    void setup() {
        requestDto = CategoryRequestDto.builder()
                .categoryTitle("Tech")
                .categoryDescription("Technology related")
                .build();

        responseDto = CategoryResponseDto.builder()
                .categoryId(1L)
                .categoryTitle("Tech")
                .categoryDescription("Technology related")
                .postCount(0)
                .build();
    }

    @Test
    void createCategory_shouldReturnCreatedCategory() throws Exception {
        when(categoryService.createCategory(any(CategoryRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryTitle").value("Tech"))
                .andExpect(jsonPath("$.categoryId").value(1));
    }

    @Test
    void deleteCategory_shouldReturnSuccessMessage() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category deleted successfully"));

        verify(categoryService).deleteCategory(1L);
    }

    @Test
    void getCategoryById_shouldReturnCategory() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryTitle").value("Tech"))
                .andExpect(jsonPath("$.categoryId").value(1));
    }
}