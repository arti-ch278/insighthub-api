package com.artichourey.insighthub.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByName_shouldReturnUser() {
        User user = User.builder()
                .name("John")
                .email("john@test.com")
                .password("1234")
                .build();

        entityManager.persistAndFlush(user);

        Optional<User> found = userRepository.findByName("John");

        assertTrue(found.isPresent());
        assertEquals("john@test.com", found.get().getEmail());
    }
	

}
