package com.artichourey.insighthub.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;

public interface CategoryService {
	

	    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

	    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

	    CategoryResponseDto getCategoryById(Long categoryId);

	    Page<CategoryResponseDto> getAllCategories(int pageNumber, int pageSize);
	    
	    List<CategoryResponseDto> getAllCategoriesNoPaging();

	    void deleteCategory(Long categoryId);
	}


