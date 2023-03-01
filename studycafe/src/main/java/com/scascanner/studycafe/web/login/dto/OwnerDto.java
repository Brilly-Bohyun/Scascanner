package com.scascanner.studycafe.web.login.dto;

import com.scascanner.studycafe.domain.entity.Owner;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerDto {

    private String email;
    private String password;
    private String nickname;
    private String name;
    private LocalDate birthday;

    @Builder
    public OwnerDto(String email, String password, String nickname, String name, LocalDate birthday) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthday = birthday;
    }

    public static OwnerDto of(Owner owner) {
          return OwnerDto.builder()
                        .email(owner.getEmail())
                        .password(owner.getPassword())
                        .nickname(owner.getNickname())
                        .name(owner.getName())
                        .birthday(owner.getBirthday())
                        .build();
    }

    public Owner toEntity() {
        return Owner.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .birthday(birthday)
                .build();
    }

    @Override
    public String toString() {
        return "Owner{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
