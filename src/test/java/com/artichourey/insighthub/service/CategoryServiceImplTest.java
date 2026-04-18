package com.artichourey.insighthub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;
import com.artichourey.insighthub.entities.Category;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.CategoryMapper;
import com.artichourey.insighthub.repositories.CategoryRepository;
import com.artichourey.insighthub.serviceImpl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequestDto requestDto;
    private CategoryResponseDto responseDto;

    @BeforeEach
    void setup() {
        category = Category.builder()
                .categoryId(1L)
                .categoryTitle("Tech")
                .categoryDescription("Technology related")
                .build();

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
    void createCategory_shouldReturnCategoryResponseDto() {
        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.createCategory(requestDto);

        assertNotNull(result);
        assertEquals("Tech", result.getCategoryTitle());
        verify(categoryRepository).save(category);
    }

    @Test
    void getCategoryById_shouldThrowException_whenNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void deleteCategory_shouldCallRepositoryDelete() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(1L);
        verify(categoryRepository).delete(category);
    }
}