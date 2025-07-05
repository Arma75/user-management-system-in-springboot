package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.ApiResponse;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    /**
     * 회원 가입 API
     * POST /users
     * @param user 가입할 사용자 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        try {
            userService.signup(user);

            return new ResponseEntity<>(new ApiResponse("회원 가입이 성공적으로 완료되었습니다.", true), HttpStatus.CREATED);
        } catch( DuplicateEmailException e ) {
            // 이메일 중복 예외 처리
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.CONFLICT);
        } catch( Exception e ) {
            // 지정되지 않은 예외 발생
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 탈퇴 API
     * DELETE /users/{userId}
     * @param userId 탈퇴할 사용자 아이디
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            //userService.withdrawal(userId);

            return new ResponseEntity<>(new ApiResponse("회원 탈퇴가 성공적으로 완료되었습니다.", true), HttpStatus.OK);
        } catch( UserNotFoundException e ) {
            // 존재하지 않는 사용자
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch( Exception e ) {
            // 지정되지 않은 예외 발생
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 정보 조회 API
     * GET /users/{userId}
     * @param userId 조회할 사용자 아이디
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserProfile(@PathVariable Long userId) {
        try {
            User user = null;
            //user = userService.getUserById(userId);

            return new ResponseEntity<>(new ApiResponse("사용자 정보를 성공적으로 조회했습니다.", true, user), HttpStatus.OK);
        } catch( UserNotFoundException e ) {
            // 존재하지 않는 사용자
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch( Exception e ) {
            // 지정되지 않은 예외 발생
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}