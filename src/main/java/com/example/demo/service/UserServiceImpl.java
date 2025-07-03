// src/main/java/com/example/demo/service/UserServiceImpl.java
package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User signup(User user) throws Exception {
        // 이메일 패턴 검사

        // 이메일 중복 검사
        if( userRepository.findByEmail(user.getEmail()).isPresent() ) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화

        // User 엔티티 생성 및 저장
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword("test");

        return userRepository.save(newUser);
    }
}
