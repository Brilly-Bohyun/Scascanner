package com.scascanner.studycafe.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String name;
    private LocalDate birthday;

    @Builder
    public User(String email, String password, String nickname, String name, LocalDate birthday) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthday = birthday;
    }
}