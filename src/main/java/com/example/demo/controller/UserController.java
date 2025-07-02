package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    /*
     * 회원가입
     * 회원탈퇴
     * 로그인
     * 로그아웃
     * 회원여부조회
     * 
     */

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        // 이메일 형식 체크

        // 이메일 중복 검사
        if( userRepository.findByEmail(user.getEmail()).isPresent() ) {
            response.put("message", "이미 존재하는 이메일입니다.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
        }

        // 비밀번호 암호화
        // user.setPassword(passwordEncoder.encode(user.getPassword())); // 예시: Spring Security PasswordEncoder 사용

        // 사용자 정보 저장
        userRepository.save(user);
        response.put("message", "회원가입이 성공적으로 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }

    /**
     * TODO: 회원 정보 조회 API
     * GET /users/{email}
     * @param email 조회할 사용자 이메일
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, String>> getUserProfile(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        return new ResponseEntity<>(response, HttpStatus.CONTINUE);
    }
}