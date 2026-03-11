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
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.repositories.PostRepository;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Category category;

    @BeforeEach
    void setup() {
        user = new User(); 
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPassword("123456");
        entityManager.persist(user);

        category = new Category();
        category.setCategoryId(null);
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("related tech");
        entityManager.persist(category);
    }

    @Test
    void savePost_shouldPersistSuccessfully() {
        Post post = Post.builder()
                .title("Test Post")
                .content("Content")
                .user(user)
                .category(category)
                .build();

        Post saved = postRepository.save(post);

        assertNotNull(saved.getPostId());
        assertEquals("Test Post", saved.getTitle());
    }

    @Test
    void findById_shouldReturnPost() {
        Post post = Post.builder()
                .title("Find Post")
                .content("Content")
                .user(user)
                .category(category)
                .build();

        entityManager.persist(post);

        Optional<Post> found = postRepository.findById(post.getPostId());
        assertTrue(found.isPresent());
        assertEquals("Find Post", found.get().getTitle());
    }

    @Test
    void deletePost_shouldRemoveFromDatabase() {
        Post post = Post.builder()
                .title("Delete Post")
                .content("Content")
                .user(user)
                .category(category)
                .build();

        entityManager.persist(post);
        postRepository.delete(post);

        Optional<Post> deleted = postRepository.findById(post.getPostId());
        assertFalse(deleted.isPresent());
    }
}