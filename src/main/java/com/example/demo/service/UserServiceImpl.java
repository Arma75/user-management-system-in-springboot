// src/main/java/com/example/demo/service/UserServiceImpl.java
package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public User signup(User user) throws Exception {
        // 이메일 존재여부 검사
        if( user.getEmail() == null || user.getEmail().trim().isEmpty() ) {
            throw new Exception("이메일을 입력해주세요.");
        }

        // 이메일 패턴 검사
        if( !Pattern.compile(EMAIL_REGEX).matcher(user.getEmail()).matches() ) {
            throw new Exception("이메일 형식이 올바르지 않습니다.");
        }

        // 이메일 중복 검사
        if( userRepository.findByEmail(user.getEmail()).isPresent() ) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        // 닉네임 존재여부 검사
        if( user.getName() == null || user.getName().trim().isEmpty() ) {
            throw new Exception("닉네임을 입력해주세요.");
        }

        // 비밀번호 암호화

        // User 엔티티 생성 및 저장
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        return userRepository.save(newUser);
    }
}
