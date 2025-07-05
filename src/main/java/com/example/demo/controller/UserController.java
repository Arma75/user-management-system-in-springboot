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

    /*
     * 회원가입
     * 회원탈퇴
     * 로그인
     * 로그아웃
     * 회원여부조회
     * 
     */

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody User user) {
        try {
            userService.signup(user);

            return new ResponseEntity<>(new ApiResponse("회원 가입이 성공적으로 완료되었습니다.", true), HttpStatus.CREATED);
        } catch( DuplicateEmailException e ) {
            // 이메일 중복 예외 처리
            return new ResponseEntity<>(new ApiResponse("이미 가입된 이메일입니다.", false), HttpStatus.CONFLICT);
        } catch( Exception e ) {
            // 지정되지 않은 예외 발생
            return new ResponseEntity<>(new ApiResponse("회원 가입이 실패하였습니다. 관리자에게 문의해주세요.", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String email) {
        try {
            //userService.withdrawal(email);

            return new ResponseEntity<>(new ApiResponse("회원 탈퇴가 성공적으로 완료되었습니다.", true), HttpStatus.OK);
        } catch( UserNotFoundException e ) {
            return new ResponseEntity<>(new ApiResponse("회원 정보가 존재하지 않습니다.", false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // 지정되지 않은 예외 발생
            return new ResponseEntity<>(new ApiResponse("회원 가입이 실패하였습니다. 관리자에게 문의해주세요.", false), HttpStatus.INTERNAL_SERVER_ERROR);
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