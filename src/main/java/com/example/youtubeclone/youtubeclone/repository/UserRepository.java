package com.example.youtubeclone.youtubeclone.repository;

import com.example.youtubeclone.youtubeclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
