package com.artichourey.insighthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artichourey.insighthub.entities.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

}
