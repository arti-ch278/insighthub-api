package com.artichourey.insighthub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artichourey.insighthub.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByName(String Name);

}
