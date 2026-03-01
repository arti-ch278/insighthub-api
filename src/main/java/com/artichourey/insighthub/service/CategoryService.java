package com.artichourey.insighthub.service;

import org.springframework.data.domain.Page;

import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;

public interface CategoryService {
	

	    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

	    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

	    CategoryResponseDto getCategoryById(Long categoryId);

	    Page<CategoryResponseDto> getAllCategories(int pageNumber, int pageSize);

	    void deleteCategory(Long categoryId);
	}


