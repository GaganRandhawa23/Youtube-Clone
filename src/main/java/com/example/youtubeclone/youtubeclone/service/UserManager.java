package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
