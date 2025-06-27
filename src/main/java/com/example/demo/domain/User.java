package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "users") // 데이터베이스 테이블 이름을 "users"로 지정합니다.
@Getter // Lombok: 모든 필드에 대한 getter 메서드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드에 대한 setter 메서드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok: 기본 생성자를 자동으로 생성합니다.
@AllArgsConstructor // Lombok: 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
public class User {
    @Id // 이 필드가 엔티티의 기본 키(Primary Key)임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID를 자동으로 생성하도록 설정
    private Long id; // 보통 기본 키는 Long 타입으로 많이 사용합니다

    @Column(nullable = false, unique = true, length = 50)
    private String email; // 이메일

    @Column(nullable = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;
}