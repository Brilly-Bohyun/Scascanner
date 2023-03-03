package com.scascanner.studycafe.web.login.dto;

import com.scascanner.studycafe.domain.entity.Owner;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerDto {

    @NotEmpty(message = "사용자 이메일은 필수 입니다.")
    @Email
    private String email;

    @NotEmpty(message = "사용자 비밀번호는 필수 입니다.")
    @Size(min = 8, max = 20, message = "8자 이상 20자 이하로 작성해야 합니다.")
    private String password;

    @Size(min = 1, max = 10, message = "1자 이상 10자 이하로 작성해야 합니다.")
    private String nickname;

    @NotEmpty(message = "사용자 이름은 필수 입니다.")
    private String name;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
                .password(new BCryptPasswordEncoder().encode(password))
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
