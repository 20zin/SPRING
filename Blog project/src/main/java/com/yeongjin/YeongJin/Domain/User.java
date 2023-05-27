package com.yeongjin.YeongJin.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;//아이디

    private String password;

    private LocalDateTime created_at;

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = LocalDateTime.now();

    }
}
//uuid = 범용 고유 식별자