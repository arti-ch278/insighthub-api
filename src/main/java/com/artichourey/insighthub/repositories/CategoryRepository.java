package com.artichourey.insighthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artichourey.insighthub.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
