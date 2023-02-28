package com.scascanner.studycafe.web.login.dto;

import com.scascanner.studycafe.domain.entity.Owner;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private LocalDate birthday;

    @Builder
    public OwnerDto(Long id, String email, String password, String nickname, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthday = birthday;
    }

    public static OwnerDto of(Owner owner) {
          return OwnerDto.builder()
                        .id(owner.getId())
                        .email(owner.getEmail())
                        .password(owner.getPassword())
                        .nickname(owner.getNickname())
                        .name(owner.getName())
                        .birthday(owner.getBirthday())
                        .build();
    }

    public Owner toEntity() {
        return Owner.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .birthday(birthday)
                .build();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
