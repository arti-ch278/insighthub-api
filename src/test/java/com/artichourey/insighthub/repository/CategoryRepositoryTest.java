package com.artichourey.insighthub.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.artichourey.insighthub.entities.Category;
import com.artichourey.insighthub.repositories.CategoryRepository;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Category category;

    @BeforeEach
    void setup() {
        category = Category.builder()
                .categoryTitle("Tech")
                .categoryDescription("Technology related posts")
                .build();
    }

    @Test
    void saveCategory_shouldPersistSuccessfully() {
        Category saved = categoryRepository.save(category);
        assertNotNull(saved.getCategoryId());
        assertEquals("Tech", saved.getCategoryTitle());
    }

    @Test
    void findById_shouldReturnCategory() {
        entityManager.persist(category);
        Optional<Category> found = categoryRepository.findById(category.getCategoryId());
        assertTrue(found.isPresent());
        assertEquals("Tech", found.get().getCategoryTitle());
    }

    @Test
    void deleteCategory_shouldRemoveFromDatabase() {
        entityManager.persist(category);
        categoryRepository.delete(category);
        Optional<Category> deleted = categoryRepository.findById(category.getCategoryId());
        assertFalse(deleted.isPresent());
    }
}