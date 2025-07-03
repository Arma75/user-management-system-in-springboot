package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {
    User signup(User user) throws Exception;
}