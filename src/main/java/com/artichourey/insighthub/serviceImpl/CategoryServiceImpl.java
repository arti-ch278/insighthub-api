package com.artichourey.insighthub.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.artichourey.insighthub.dtos.CategoryRequestDto;
import com.artichourey.insighthub.dtos.CategoryResponseDto;
import com.artichourey.insighthub.entities.Category;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.CategoryMapper;
import com.artichourey.insighthub.repositories.CategoryRepository;
import com.artichourey.insighthub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
		  log.info("Creating new category with title: {}", categoryRequestDto.getCategoryTitle());

	      Category category=categoryMapper.toEntity(categoryRequestDto);
	      Category savedCategory=categoryRepository.save(category);
	      log.info("Category created successfully with id: {}", savedCategory.getCategoryId());
		return categoryMapper.toDto(savedCategory);
	}

	@Override
	public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
		log.info("Updating category with id: {}", categoryId);
		Category category=categoryRepository.findById(categoryId).orElseThrow
		(()-> new ResourceNotFoundException("Category id not found with this"+categoryId));
		category.setCategoryDescription(categoryRequestDto.getCategoryDescription());
		category.setCategoryTitle(categoryRequestDto.getCategoryTitle());
		categoryRepository.save(category);
		log.info("Category updated successfully with id: {}", categoryId);
		return categoryMapper.toDto(category);
	}

	@Override
	public CategoryResponseDto getCategoryById(Long categoryId) {
		log.info("Fetching category with id: {}", categoryId);
		Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with this"+categoryId));
		log.info("Category fetched successfully with id: {}", categoryId);
		return categoryMapper.toDto(category);
	}

	@Override
	public Page<CategoryResponseDto> getAllCategories(int pageNumber, int pageSize) {
		log.info("Fetching all categories - pageNumber: {}, pageSize: {}", pageNumber, pageSize);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("categoryTitle").ascending());
        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);

        log.info("Fetched {} categories on page {}", categoryPage.getNumberOfElements(), pageNumber);
        return categoryPage.map(categoryMapper::toDto);
		
	}

	@Override
	public void deleteCategory(Long categoryId) {
		 log.info("Deleting category with id: {}", categoryId);
		Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with this"+categoryId));
		categoryRepository.delete(category);
		log.info("Category deleted successfully with id: {}", categoryId);

	}

	@Override
	public List<CategoryResponseDto> getAllCategoriesNoPaging() {
		log.info("Fetching all categories ");
	    List<Category> categories = categoryRepository.findAll(Sort.by("categoryTitle").ascending());
	    log.info("Fetched {} categories", categories.size());
	    return categories.stream()
	                     .map(categoryMapper::toDto)
	                     .collect(Collectors.toList());
	}

}
