package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // save(user), findById(id), findAll(), delete(user)
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);
}