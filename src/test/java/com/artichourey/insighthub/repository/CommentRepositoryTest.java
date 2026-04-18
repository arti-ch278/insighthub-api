package com.artichourey.insighthub.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.artichourey.insighthub.entities.Comment;
import com.artichourey.insighthub.entities.Post;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.repositories.CommentRepository;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Post post;
    private User user;
    private Comment comment;

    @BeforeEach
    void setup() {
        user = User.builder().name("John").email("john@test.com").password("123456").build();
        entityManager.persist(user);

        post = Post.builder().title("Test Post").content("Content").user(user).build();
        entityManager.persist(post);

        comment = Comment.builder().content("Nice post!").post(post).user(user).build();
        entityManager.persist(comment);
    }

    @Test
    void saveComment_shouldPersistSuccessfully() {
        Comment saved = commentRepository.save(comment);
        assertNotNull(saved.getId());
        assertEquals("Nice post!", saved.getContent());
    }

    @Test
    void findById_shouldReturnComment() {
        Optional<Comment> found = commentRepository.findById(comment.getId());
        assertTrue(found.isPresent());
        assertEquals("Nice post!", found.get().getContent());
    }

    @Test
    void findByPostPostId_shouldReturnList() {
        List<Comment> comments = commentRepository.findByPostPostId(post.getPostId());
        assertFalse(comments.isEmpty());
        assertEquals("Nice post!", comments.get(0).getContent());
    }

    @Test
    void deleteComment_shouldRemoveFromDatabase() {
        commentRepository.delete(comment);
        Optional<Comment> deleted = commentRepository.findById(comment.getId());
        assertFalse(deleted.isPresent());
    }
}
