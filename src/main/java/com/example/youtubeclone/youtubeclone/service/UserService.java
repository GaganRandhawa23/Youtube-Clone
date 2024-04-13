package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.User;

public interface UserService {
    public User getUserByEmail(String email);
    public void saveUser(User user);
}
