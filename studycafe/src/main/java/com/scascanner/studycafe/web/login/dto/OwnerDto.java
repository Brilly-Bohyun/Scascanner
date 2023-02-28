package com.scascanner.studycafe.web.login.dto;

import com.scascanner.studycafe.domain.entity.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OwnerDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
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
