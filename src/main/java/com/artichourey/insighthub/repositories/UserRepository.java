package com.artichourey.insighthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artichourey.insighthub.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
