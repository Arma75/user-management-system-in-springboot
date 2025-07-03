package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

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
    private final UserService userService;

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
        try {
            userService.signup(user);

            response.put("message", "회원가입이 성공적으로 완료되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch( Exception e ) {
            // 이메일 중복 예외 처리
            response.put("message", "이미 가입된 이메일입니다.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
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